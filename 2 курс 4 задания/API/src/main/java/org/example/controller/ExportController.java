package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.PDFExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
@Tag(name = "Export API", description = "API для экспорта результатов голосования")
public class ExportController {

    @Autowired
    private PDFExportService pdfExportService;

    @PostMapping("/pdf/single")
    @Operation(summary = "Экспорт результатов одного голосования в PDF")
    public ResponseEntity<Resource> exportSingleVotingToPDF(
            @RequestParam Long votingId,
            @RequestParam(defaultValue = "candidate") String groupBy,
            @RequestParam(defaultValue = "votes") String sortBy,
            @RequestParam(defaultValue = "voting_results") String fileName) {
        
        try {
            String filePath = pdfExportService.exportSingleVoting(votingId, groupBy, sortBy, fileName);
            Resource resource = new FileSystemResource(filePath);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
                    
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/pdf/multiple")
    @Operation(summary = "Экспорт результатов нескольких голосований в PDF")
    public ResponseEntity<Resource> exportMultipleVotingsToPDF(
            @RequestBody List<Long> votingIds,
            @RequestParam(defaultValue = "candidate") String groupBy,
            @RequestParam(defaultValue = "votes") String sortBy,
            @RequestParam(defaultValue = "multiple_voting_results") String fileName,
            @RequestParam(defaultValue = "true") boolean singleFile) {
        
        try {
            String filePath = pdfExportService.exportMultipleVotings(
                votingIds, groupBy, sortBy, fileName, singleFile);
            Resource resource = new FileSystemResource(filePath);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
                    
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 