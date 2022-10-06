package com.lab3.info.service.save;

import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.entity.Report;
import com.lab3.info.exception.ReportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

public class XmlFileCreator implements Savable {
    private final Logger LOGGER = LoggerFactory.getLogger(XmlFileCreator.class);

    @Override
    public String save(ReportCardDto reportCard, String saveDir) throws ReportException {
        String fileName = reportCard
                .getStudentName()
                .replace(" ", "_") + "_report.xml";
        String pathToSave = saveDir + FileSystems.getDefault().getSeparator() + fileName;
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            reportToXml(document, reportCard);
            writeToFile(pathToSave, document);
            LOGGER.info("Create file " + pathToSave + " successful");
            return fileName;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e.getCause());
            throw new ReportException("Error create a file " + pathToSave);
        }

    }

    private void reportToXml(Document doc, ReportCardDto reportCard) {
        Element root = doc.createElement("reportDTO");
        doc.appendChild(root);
        Element elem = doc.createElement("studentName");
        elem.setTextContent(reportCard.getStudentName());
        root.appendChild(elem);
        elem = doc.createElement("studentGroup");
        elem.setTextContent(reportCard.getStudentGroup());
        root.appendChild(elem);
        elem = doc.createElement("reports");
        addReportsList(doc, elem, reportCard.getReports());
        root.appendChild(elem);

    }

    private void addReportsList(Document doc, Element root, List<Report> reports) {
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

    private void writeToFile(String fileName, Document document) {
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
