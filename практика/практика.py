import asyncio
import nest_asyncio

from telegram import Update, InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import Application, CommandHandler, CallbackQueryHandler, CallbackContext, MessageHandler, filters
import os
import zipfile
import io
import httpx

TOKEN = '6447059182:AAGyIHsVgI_uUnOAP3Y8SoqYg0oG2Szk-Lw'
UNPLASH_API_KEY = 'BFzscqOw57170cRzcDREQ8bYOsfTlLuWdVyRz64sDfs'
async def start(update: Update, context: CallbackContext):
    await update.message.reply_text('Введите ключевую фразу')

async def handle_message(update: Update, context: CallbackContext):
    context.user_data['phrase'] = update.message.text
    keyboard = [
        [InlineKeyboardButton(str(i), callback_data=str(i)) for i in range(1, 11)]
    ]
    reply_markup = InlineKeyboardMarkup(keyboard)
    await update.message.reply_text('Выберите количество изображений:', reply_markup=reply_markup)

async def button(update: Update, context: CallbackContext):
    query = update.callback_query
    await query.answer()
    count = int(query.data)
    phrase = context.user_data.get('phrase')

    loading_message = await query.message.reply_text('Загрузка')

    await send_filtered_images(query, phrase, count, loading_message)

async def search_unsplash_images(query, api_key, count=10):
    url = "https://api.unsplash.com/search/photos"
    headers = {"Authorization": f"Client-ID {api_key}"}
    params = {"query": query, "per_page": count}
    async with httpx.AsyncClient() as client:
        response = await client.get(url, headers=headers, params=params)
        response.raise_for_status()
        search_results = response.json()

        images_urls = [item["urls"]["regular"] for item in search_results["results"]]
        return images_urls
async def send_filtered_images(query, phrase, count, loading_message):
    try:
        images_urls = await search_unsplash_images(phrase, UNPLASH_API_KEY, count)
        zip_buffer = io.BytesIO()

        async with httpx.AsyncClient() as client:
            with zipfile.ZipFile(zip_buffer, 'a', zipfile.ZIP_DEFLATED, False) as zip_file:
                for i, url in enumerate(images_urls):
                    response = await client.get(url)
                    image_name = f"image_{i}.jpg"
                    zip_file.writestr(image_name, response.content)

        zip_buffer.seek(0)
        await query.message.reply_document(document=zip_buffer, filename=phrase+'.zip')
    except Exception as e:
        await query.message.reply_text(f"Произошла ошибка: {e}")
    finally:
        await loading_message.delete()

async def main():
    application = Application.builder().token(TOKEN).build()

    application.add_handler(CommandHandler('start', start))
    application.add_handler(MessageHandler(filters.TEXT, handle_message))
    application.add_handler(CallbackQueryHandler(button))

    await application.run_polling()


if __name__ == '__main__':
    nest_asyncio.apply()
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main())