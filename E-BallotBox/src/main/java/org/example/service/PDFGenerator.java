//package org.example.service;
//
//import org.example.model.Voting;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class PDFGenerator {
//
//    public void generatePDF(Voting voting, String filePath) {
//        try (PDDocument doc = new PDDocument()) {
//            PDPage page = new PDPage();
//            doc.addPage(page);
//
//            PDPageContentStream content = new PDPageContentStream(doc, page);
//            content.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            content.beginText();
//            content.newLineAtOffset(50, 700);
//            content.showText("Результаты голосования: " + voting.getTitle());
//            content.newLineAtOffset(0, -20);
//            content.showText("Дата окончания: " + new SimpleDateFormat("dd.MM.yyyy").format(voting.getEndDate()));
//
//            // Таблица с кандидатами и голосами
//            int y = 650;
//            for (var entry : voting.getVotes().entrySet()) {
//                content.newLineAtOffset(0, -20);
//                content.showText(entry.getKey().getInfo().getName() + ": " + entry.getValue());
//                y -= 20;
//            }
//
//            content.endText();
//            content.close();
//            doc.save(filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void generateCombinedPDF(List<Voting> votings, String filePath) {
//        try (PDDocument doc = new PDDocument()) {
//            PDPage currentPage = null;
//            PDPageContentStream content = null;
//            int yPosition = 700; // Стартовая позиция Y на странице
//            int pageCounter = 0;
//
//            for (Voting voting : votings) {
//                // Создаём новую страницу при первом голосовании или если не осталось места
//                if (currentPage == null || yPosition < 100) {
//                    if (content != null) {
//                        content.close(); // Закрываем предыдущий поток
//                    }
//                    currentPage = new PDPage();
//                    doc.addPage(currentPage);
//                    content = new PDPageContentStream(doc, currentPage);
//                    content.setFont(PDType1Font.HELVETICA_BOLD, 12);
//                    yPosition = 700; // Сброс позиции
//                    pageCounter++;
//                }
//
//                // Заголовок голосования
//                content.beginText();
//                content.newLineAtOffset(50, yPosition);
//                content.showText("Голосование #" + pageCounter + ": " + voting.getTitle());
//                content.newLineAtOffset(0, -20);
//                content.showText("Дата окончания: " +
//                        new SimpleDateFormat("dd.MM.yyyy").format(voting.getEndDate()));
//                content.endText();
//                yPosition -= 40;
//
//                // Таблица с результатами
//                content.beginText();
//                content.newLineAtOffset(50, yPosition);
//                for (var entry : voting.getVotes().entrySet()) {
//                    String line = String.format(
//                            "%-30s %d голосов",
//                            entry.getKey().getInfo().getName(),
//                            entry.getValue()
//                    );
//                    content.showText(line);
//                    content.newLineAtOffset(0, -20);
//                    yPosition -= 20;
//
//                    // Если осталось мало места, создаём новую страницу
//                    if (yPosition < 100) {
//                        content.endText();
//                        content.close();
//                        currentPage = new PDPage();
//                        doc.addPage(currentPage);
//                        content = new PDPageContentStream(doc, currentPage);
//                        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
//                        content.beginText();
//                        content.newLineAtOffset(50, 700);
//                        yPosition = 700;
//                    }
//                }
//                content.endText();
//                yPosition -= 40; // Отступ между голосованиями
//            }
//
//            if (content != null) {
//                content.close();
//            }
//            doc.save(filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
