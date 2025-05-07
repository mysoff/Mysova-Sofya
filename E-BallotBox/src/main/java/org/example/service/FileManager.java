//package org.example.service;
//
//import org.example.model.Voting;
//import java.util.List;
//import java.nio.file.Path;
//
//public class FileManager {
//    private PDFGenerator pdfGenerator;
//
//    public FileManager() {
//        this.pdfGenerator = new PDFGenerator();
//    }
//
//    // Выгрузка результатов
//    public void exportToPDF(List<Voting> votings, String directory, boolean singleFile) {
//        if (singleFile) {
//            pdfGenerator.generateCombinedPDF(votings, Path.of(directory, "results.pdf").toString());
//        } else {
//            for (Voting voting : votings) {
//                String fileName = voting.getTitle().replace(" ", "_") + ".pdf";
//                pdfGenerator.generatePDF(voting, Path.of(directory, fileName).toString());
//            }
//        }
//    }
//}
