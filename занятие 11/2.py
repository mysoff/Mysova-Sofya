import requests
import tkinter as tk
import json
def get_json(name):
    url = f"https://api.github.com/users/{name}"
    response=requests.get(url)
    if response.status_code == 200:
        return response.json()
    else:
        return None
def get_r():
    name = entry.get()
    b=get_json(name)
    if b:
        k= {
            'company': None,
            'created_at': b['created_at'],
            'email': None,
            'id': b['id'],
            'name': b['name'],
            'url':b['url']
            }

        with open('информация.json', "w") as f:
            json.dump(k, f,indent=2)
            print("Информация сохранена в 'информация.json'")
    else:
        print("Неправильное имя репозитория")
window = tk.Tk()
window.title("Репозиторий")
window.geometry("400x300")
label=tk.Label(window, text="Введите имя репозитория:")
label.pack()

entry = tk.Entry(window)
entry.pack()

button=tk.Button(window, text="Готово", command=get_r)
button.pack()
window.mainloop()

