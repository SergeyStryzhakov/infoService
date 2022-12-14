package com.lab3.info.service.save;

import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.entity.Report;
import com.lab3.info.exception.ReportException;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;

public class WordFileCreator implements Savable {
    private final Logger LOGGER = LoggerFactory.getLogger(WordFileCreator.class);

    public String save(ReportCardDto reportCard, String saveDir) throws ReportException {
        String fileName = reportCard
                .getStudentName()
                .replace(" ", "_") + "_report.docx";
        String pathToSave = saveDir + FileSystems.getDefault().getSeparator() + fileName;
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun p = paragraph.createRun();
        p.setBold(true);
        p.setFontSize(36);
        p.setText("Report card");
        p.addBreak();
        p.addBreak();
        p.setFontSize(22);
        p.setText(reportCard.getStudentName());
        p.addBreak();
        p.addBreak();

        XWPFTable table = document.createTable(1, 4);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth("100%");

        XWPFTableRow row = table.getRow(0);
        configureCell(row.getCell(0), "No", true, true, 16);
        configureCell(row.getCell(1), "Subject", true, true, 16);
        configureCell(row.getCell(2), "Mark", true, true, 16);
        configureCell(row.getCell(3), "Letter", true, true, 16);

        for (int i = 0; i < reportCard.getReports().size(); i++) {
            Report report = reportCard.getReports().get(i);
            row = table.createRow();
            configureCell(row.getCell(0), String.valueOf(i + 1), true, false, 14);
            configureCell(row.getCell(1), report.getSubjectName(), false, true, 14);
            configureCell(row.getCell(2), report.getMarkValue(), true, true, 16);
            configureCell(row.getCell(3), report.getGrade(), true, true, 16);
        }
        XWPFParagraph secondParagraph = document.createParagraph();
        XWPFRun secondP = secondParagraph.createRun();
        secondP.addBreak();
        secondP.setFontSize(18);
        secondP.setText("Head teacher _________________________________");
        secondP.addBreak();
        XWPFParagraph thirdParagraph = document.createParagraph();
        XWPFRun thirdP = thirdParagraph.createRun();
        thirdP.addBreak();
        thirdP.addBreak();
        thirdP.setFontSize(10);
        thirdP.setText("Created " + new Date());
        try (FileOutputStream os = new FileOutputStream(pathToSave)) {
            document.write(os);
            LOGGER.info("Create file " + pathToSave + " successful");
            return fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.getCause());
            throw new ReportException("Can`t find file " + pathToSave);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.getCause());
            throw new ReportException("Error while writing a file " + pathToSave);
        }
    }

    private void configureCell(XWPFTableCell cell, String data,
                               boolean isCenter, boolean isBold, int fontSize) {
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        cell.setText(data);
        if (isCenter) cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        cell.getParagraphs().get(0).getRuns().get(0).setBold(isBold);
        cell.getParagraphs().get(0).getRuns().get(0).setFontSize(fontSize);
    }

}
