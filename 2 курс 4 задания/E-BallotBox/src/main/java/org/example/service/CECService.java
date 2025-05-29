package org.example.service;

import org.example.config.DatabaseConnection;
import org.example.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.awt.Color;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class CECService {
    public boolean createVoting(int commissionId, LocalDate endDate) {
        String sql = "INSERT INTO votings (commission_id, end_date) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commissionId);
            stmt.setDate(2, Date.valueOf(endDate));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addCandidate(String login, String password, String fullName, String info, int commissionId) {
        String sql = "INSERT INTO candidates (login, password, full_name, info, created_by_commission_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, fullName);
            stmt.setString(4, info);
            stmt.setInt(5, commissionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignCandidateToVoting(int candidateId, int votingId) {
        String sql = "INSERT INTO candidate_voting (candidate_id, voting_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candidateId);
            stmt.setInt(2, votingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Voting> getActiVotings(int commissionId) {
        List<Voting> votings = new ArrayList<>();
        String sql = "SELECT * FROM votings WHERE commission_id = ? AND end_date >= CURRENT_DATE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commissionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                votings.add(new Voting(
                        rs.getInt("id"),
                        rs.getInt("commission_id"),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votings;
    }

    public List<Voting> getAllVotings(int commissionId) {
        List<Voting> votings = new ArrayList<>();
        String sql = "SELECT * FROM votings WHERE commission_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commissionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                votings.add(new Voting(
                        rs.getInt("id"),
                        rs.getInt("commission_id"),
                        rs.getDate("end_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votings;
    }

    public List<Candidate> getAllCandidates(int commissionId) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE created_by_commission_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commissionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                candidates.add(new Candidate(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("info"),
                        rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    // Получение результатов с группировкой и сортировкой
    public List<VotingResult> getVotingResults(int votingId, String groupBy, String sortBy) {
        List<VotingResult> results = new ArrayList<>();

        // Определение столбца для группировки
        String groupByColumn;
        switch (groupBy.toLowerCase()) {
            case "voting":
                groupByColumn = "v.voting_id";
                break;
            case "candidate":
            default:
                groupByColumn = "c.full_name";
                break;
        }

        // Динамический SQL-запрос
        String sql = "SELECT " + groupByColumn + " AS group_key, COUNT(v.id) as votes " +
                "FROM votes v " +
                "JOIN candidates c ON v.candidate_id = c.id " +
                "WHERE v.voting_id = ? " +
                "GROUP BY " + groupByColumn;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, votingId);
            ResultSet rs = stmt.executeQuery();

            // Формирование результатов
            while (rs.next()) {
                String groupKey = rs.getString("group_key");
                int votes = rs.getInt("votes");
                results.add(new VotingResult(groupKey, votes));
            }

            // Сортировка
            if ("name".equalsIgnoreCase(sortBy)) {
                results.sort(Comparator.comparing(VotingResult::getCandidateName));
            } else {
                results.sort((a, b) -> Integer.compare(b.getVotes(), a.getVotes()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void exportMultipleVotings(List<Integer> votingIds, String outputPath,
                                      boolean singleFile, String groupBy,
                                      String sortBy, String fileName) throws IOException {
        List<VotingResult> results = new ArrayList<>();

        if (singleFile) {
            // Собираем все результаты с группировкой
            for (int id : votingIds) {
                results.addAll(getVotingResults(id, groupBy, sortBy));
            }
            generatePDF(results, outputPath, fileName, votingIds);
        } else {
            // Генерируем отдельные файлы
            for (int id : votingIds) {
                List<VotingResult> votingResults = getVotingResults(id, groupBy, sortBy);
                generatePDF(votingResults, outputPath, fileName + "_" + id, List.of(id));
            }
        }
    }

    private void generatePDF(List<VotingResult> results, String outputPath,
                             String fileName, List<Integer> votingIds) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font font = loadFont(document);
            PDPageContentStream cs = new PDPageContentStream(document, page);

            // Настройки документа
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;

            // Заголовок документа
            cs.setFont(font, 16);
            cs.beginText();
            cs.newLineAtOffset(margin, yStart);
            cs.showText("ОФИЦИАЛЬНЫЙ ОТЧЕТ О РЕЗУЛЬТАТАХ ГОЛОСОВАНИЯ");
            cs.endText();
            yStart -= 30;

            // Информация о голосованиях
            cs.setFont(font, 12);
            cs.beginText();
            cs.newLineAtOffset(margin, yStart);
            cs.showText("Голосования: " + votingIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));
            cs.newLineAtOffset(0, -20);
            cs.showText("Дата формирования: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            cs.endText();
            yStart -= 40;

            // Заголовок таблицы
            cs.setFont(font, 12);
            drawTableRow(cs, margin, yStart, tableWidth,
                    new String[]{"Наименование", "Количество голосов"}, true);
            yStart -= 20;

            // Данные таблицы
            cs.setFont(font, 10);
            for (VotingResult result : results) {
                if (yStart < margin + 30) {
                    cs.close(); // Закрываем текущий поток
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    cs = new PDPageContentStream(document, page); // Новый поток для новой страницы
                    yStart = page.getMediaBox().getHeight() - margin;
                }

                drawTableRow(cs, margin, yStart, tableWidth,
                        new String[]{
                                result.getCandidateName(),
                                String.valueOf(result.getVotes())
                        },
                        false);
                yStart -= 20;
            }

            // Подпись
            cs.setFont(font, 10);
            cs.beginText();
            cs.newLineAtOffset(margin, yStart - 30);
            cs.showText("Председатель ЦИК: _________________________");
            cs.endText();
            cs.close();

            // Сохранение файла
            Path dirPath = Paths.get(outputPath);
            if (!Files.exists(dirPath)) Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(fileName + ".pdf");
            document.save(filePath.toFile());
        }
    }

    private PDType0Font loadFont(PDDocument document) throws IOException {
        try {
            return PDType0Font.load(document, new File("C:\\Windows\\Fonts\\arial.ttf"));
        } catch (IOException e) {
            return PDType0Font.load(document, new File("/usr/share/fonts/truetype/freefont/FreeSans.ttf"));
        }
    }

    // Метод для рисования строки таблицы
    private void drawTableRow(PDPageContentStream cs, float x, float y,
                              float width, String[] columns, boolean isHeader)
            throws IOException {
        float colWidth = width / columns.length;
        float padding = 5;

        // Фон для заголовка
        if (isHeader) {
            cs.setNonStrokingColor(Color.LIGHT_GRAY);
            cs.addRect(x, y - 20, width, 20);
            cs.fill();
            cs.setNonStrokingColor(Color.BLACK);
        }

        // Текст и границы
        for (int i = 0; i < columns.length; i++) {
            float currentX = x + (colWidth * i); // Начало текущей колонки
            float textX = currentX + padding; // Позиция текста с отступом

            // Отрисовка текста
            cs.beginText();
            cs.newLineAtOffset(textX, y - 12); // Смещаемся только по X для каждой колонки
            cs.showText(columns[i]);
            cs.endText();

            // Вертикальные границы
            cs.moveTo(currentX, y);
            cs.lineTo(currentX, y - 20);
            cs.stroke();
        }

        // Горизонтальные границы
        cs.moveTo(x, y);
        cs.lineTo(x + width, y);
        cs.stroke();
        cs.moveTo(x, y - 20);
        cs.lineTo(x + width, y - 20);
        cs.stroke();
    }
}


