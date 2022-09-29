package com.lab3.info.util;


import com.lab3.info.dto.ReportCardDTO;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordFileCreator {
    private ReportCardDTO report;
    private String path;

    public String save(ReportCardDTO report, String path) {
        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.createRow();
        row.addNewTableCell().setText("test cell 1");
        row.addNewTableCell().setText("test cell 2");
        XWPFTableRow row1 = table.createRow();
        row1.addNewTableCell().setText("test cell 1 in row2");
        row1.addNewTableCell().setText("test cell 2 in row 2");

        return null;
    }
}
