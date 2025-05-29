package org.example.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.example.dto.VotingResultDTO;
import org.example.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class PDFExportService {

    @Autowired
    private VoteRepository voteRepository;

    @Value("${app.pdf.output-directory:./results/}")
    private String outputDirectory;

    @Cacheable(value = "pdf-exports", key = "#votingId + '_' + #groupBy + '_' + #sortBy")
    public String exportSingleVoting(Long votingId, String groupBy, String sortBy, String fileName) throws IOException {
        List<Object[]> results = voteRepository.getVotingResults(votingId);
        
        List<VotingResultDTO> resultDTOs = results.stream()
            .map(result -> new VotingResultDTO(
                (Long) result[0],           // candidateId
                "Кандидат " + result[0],    // candidateName
                (Long) result[1],           // votesCount
                votingId
            ))
            .toList();

        // Сортировка
        if ("name".equalsIgnoreCase(sortBy)) {
            resultDTOs = resultDTOs.stream()
                .sorted(Comparator.comparing(VotingResultDTO::getCandidateName))
                .toList();
        } else {
            resultDTOs = resultDTOs.stream()
                .sorted((a, b) -> Long.compare(b.getVotesCount(), a.getVotesCount()))
                .toList();
        }

        return generatePDF(resultDTOs, fileName, List.of(votingId));
    }

    public String exportMultipleVotings(List<Long> votingIds, String groupBy, String sortBy, 
                                       String fileName, boolean singleFile) throws IOException {
        if (singleFile) {
            List<Object[]> results = voteRepository.getMultipleVotingResults(votingIds);
            
            List<VotingResultDTO> resultDTOs = results.stream()
                .map(result -> new VotingResultDTO(
                    (Long) result[0],           // candidateId
                    "Кандидат " + result[0],    // candidateName
                    (Long) result[1]            // votesCount
                ))
                .toList();

            return generatePDF(resultDTOs, fileName, votingIds);
        } else {
            // Генерируем отдельные файлы для каждого голосования
            String lastFilePath = "";
            for (Long votingId : votingIds) {
                lastFilePath = exportSingleVoting(votingId, groupBy, sortBy, fileName + "_" + votingId);
            }
            return lastFilePath; // Возвращаем путь к последнему файлу
        }
    }

    private String generatePDF(List<VotingResultDTO> results, String fileName, List<Long> votingIds) throws IOException {
        // Создаём директорию если её нет
        Path outputPath = Paths.get(outputDirectory);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fullFileName = fileName + "_" + timestamp + ".pdf";
        String filePath = outputPath.resolve(fullFileName).toString();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Загружаем шрифт
                PDType0Font font = loadFont(document);
                contentStream.setFont(font, 12);

                // Заголовок
                float yPosition = 750;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Результаты голосования");
                contentStream.endText();

                yPosition -= 30;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Голосования: " + votingIds.toString());
                contentStream.endText();

                yPosition -= 30;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Дата создания: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                contentStream.endText();

                // Заголовки таблицы
                yPosition -= 50;
                String[] headers = {"ID Кандидата", "Имя кандидата", "Количество голосов"};
                drawTableRow(contentStream, 50, yPosition, 500, headers, true, font);

                // Данные
                yPosition -= 25;
                for (VotingResultDTO result : results) {
                    String[] row = {
                        result.getCandidateId().toString(),
                        result.getCandidateName(),
                        result.getVotesCount().toString()
                    };
                    drawTableRow(contentStream, 50, yPosition, 500, row, false, font);
                    yPosition -= 20;
                    
                    if (yPosition < 50) {
                        // Создаём новую страницу если места не хватает
                        page = new PDPage();
                        document.addPage(page);
                        contentStream.close();
                        PDPageContentStream newContentStream = new PDPageContentStream(document, page);
                        yPosition = 750;
                        drawTableRow(newContentStream, 50, yPosition, 500, headers, true, font);
                        yPosition -= 25;
                    }
                }
            }

            document.save(filePath);
        }

        return filePath;
    }

    private PDType0Font loadFont(PDDocument document) throws IOException {
        // Используем встроенный шрифт для поддержки кириллицы
        return PDType0Font.load(document, 
            getClass().getResourceAsStream("/fonts/DejaVuSans.ttf"));
    }

    private void drawTableRow(PDPageContentStream cs, float x, float y, float width, 
                             String[] columns, boolean isHeader, PDType0Font font) throws IOException {
        float columnWidth = width / columns.length;
        
        cs.setFont(font, isHeader ? 12 : 10);
        
        for (int i = 0; i < columns.length; i++) {
            cs.beginText();
            cs.newLineAtOffset(x + (i * columnWidth), y);
            cs.showText(columns[i]);
            cs.endText();
        }
        
        // Рисуем линию под заголовком
        if (isHeader) {
            cs.moveTo(x, y - 5);
            cs.lineTo(x + width, y - 5);
            cs.stroke();
        }
    }
} 