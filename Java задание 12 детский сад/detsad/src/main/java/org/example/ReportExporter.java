package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class ReportExporter {

    public static void exportToPDF(String content, NurseryGUI gui) {
        try {
            File file = new File("report.pdf");
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph(content));
            document.close();
            JOptionPane.showMessageDialog(gui, "Отчет успешно сохранен в формате PDF");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Ошибка при сохранении отчета в формате PDF: " + ex.getMessage());
        }
    }

    public static void exportToTXT(String content, NurseryGUI gui) {
        try {
            File file = new File("report.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            JOptionPane.showMessageDialog(gui, "Отчет успешно сохранен в формате TXT");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Ошибка при сохранении отчета в формате TXT: " + ex.getMessage());
        }
    }
}