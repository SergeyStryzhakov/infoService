package com.lab3.info.util;


import com.lab3.info.dto.ReportCardDTO;
import com.lab3.info.entity.Report;
import org.apache.poi.ss.formula.functions.Now;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WordFileCreator {
    private ReportCardDTO report;
    private String path;

    public String save(ReportCardDTO reportCard, String path) {
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
        secondP.setText("Teacher _________________________________");
        secondP.addBreak();
        XWPFParagraph thirdParagraph = document.createParagraph();
        //thirdParagraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun thirdP = thirdParagraph.createRun();
        thirdP.addBreak();
        thirdP.addBreak();
        thirdP.setFontSize(10);
        thirdP.setText("Created " + new Date());

        try (FileOutputStream os = new FileOutputStream(path)) {
            document.write(os);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error!";
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
