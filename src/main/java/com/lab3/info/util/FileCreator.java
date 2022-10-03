package com.lab3.info.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDTO;
import com.lab3.info.entity.Report;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class FileCreator {




    private static String saveJSON(ReportCardDTO report, String pathReport) throws IOException {
        File file = new File(pathReport + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(file, report);
        return file.getName();
    }

    public static String saveXml(ReportCardDTO report, String pathReport) throws ParserConfigurationException {
        String fileName = pathReport + ".xml";
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();
        reportToXml(document, report);
        writeToFile(fileName, document);
        return fileName;
    }

    private static void reportToXml(Document doc, ReportCardDTO report) {
        Element root = doc.createElement("reportDTO");
        doc.appendChild(root);
        Element elem = doc.createElement("studentName");
        elem.setTextContent(report.getStudentName());
        root.appendChild(elem);
        elem = doc.createElement("studentGroup");
        elem.setTextContent(report.getStudentGroup());
        root.appendChild(elem);
        elem = doc.createElement("reports");
        addReportsList(doc, elem, report.getReports());
        root.appendChild(elem);

    }

    private static void addReportsList(Document doc, Element root, List<Report> reports) {
        for (Report report : reports) {
            Element rep = doc.createElement("report");
            Element elem = doc.createElement("subjectName");
            elem.setTextContent(report.getSubjectName());
            rep.appendChild(elem);
            elem = doc.createElement("markValue");
            elem.setTextContent(report.getMarkValue());
            rep.appendChild(elem);
            elem = doc.createElement("grade");
            elem.setTextContent(report.getGrade());
            rep.appendChild(elem);
            root.appendChild(rep);
        }
    }

    private static void writeToFile(String fileName, Document document) {
        try (FileOutputStream output = new FileOutputStream(fileName)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }


}
