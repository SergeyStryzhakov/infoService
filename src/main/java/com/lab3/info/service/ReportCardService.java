package com.lab3.info.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.entity.Mark;
import com.lab3.info.entity.Report;
import com.lab3.info.entity.Student;
import com.lab3.info.entity.Subject;
import com.lab3.info.exception.ReportException;
import com.lab3.info.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReportCardService {
    @Value("${api.url}")
    private String API_URL;
    private final Logger LOGGER = LoggerFactory.getLogger(ReportCardService.class);

    public ReportCardDto createDtoById(int id) throws ReportException {
        Student student = getStudent(id);
        ReportCardDto result = new ReportCardDto.Builder()
                .studentName(student.getFirstName() + " " + student.getLastName())
                .studentGroup(student.getGroupName())
                .reports(createReportList(id))
                .build();
        LOGGER.info("DTO created successfully. Student id => " + id);
        return result;
    }

    public List<Student> getStudents() throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "students");
            if (response.isEmpty()) {
                LOGGER.error("No student found, check URL => " + API_URL + "students");
                throw new ReportException("No student found, check URL");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List<Student> students = Arrays.asList(objectMapper.readValue(response, Student[].class));
            LOGGER.info("Students list created successfully.\n Have " + students.size() + " students");
            return students;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("Get students: " + e.getMessage(), e.getCause());
            throw new ReportException("Error during create students list");
        }
    }

    private List<Report> createReportList(int id) throws ReportException {
        Map<String, ArrayList<Mark>> marksBySubject;
        List<Report> reports = new ArrayList<>();
        List<Mark> marks = getMarksList(id);
        List<Subject> subjects = getSubjectList();

        marksBySubject = subjects
                .stream()
                .collect(Collectors
                        .toMap(Subject::getTitle,
                                subject -> new ArrayList<>()));


        for (Mark mark : marks) {
            marksBySubject.get(mark.getSubject().getTitle()).add(mark);
        }

        for (String subject : marksBySubject.keySet()) {
            double averageMark = marksBySubject.get(subject)
                    .stream()
                    .mapToInt(Mark::getValue)
                    .average()
                    .orElse(0.00);
            reports.add(new Report(subject, addAverageMark(averageMark), addGradeLetter(averageMark)));
        }
        LOGGER.info("Report list created successfully. ");
        return reports;
    }

    private String addAverageMark(double averageMark) {
        return averageMark != 0 ?
                String.format("%.2f", averageMark)
                : "---";
    }

    private String addGradeLetter(double averageMark) {
        if (averageMark == 0) return "---";
        if (averageMark > 4) return "A";
        if (averageMark <= 4 && averageMark > 3) return "B";
        if (averageMark <= 3 && averageMark > 2) return "C";
        return "D";
    }

    private List<Subject> getSubjectList() throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "subjects");
            ObjectMapper objectMapper = new ObjectMapper();
            List<Subject> subjects =
                    Arrays.asList(objectMapper.readValue(response, Subject[].class));
            LOGGER.info("Subjects list created successfully.\n Have " + subjects.size() + " subjects");
            return subjects;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("Get subjects: " + e.getMessage(), e.getCause());
            throw new ReportException("Error during create subject list");
        }
    }

    private Student getStudent(int id) throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "students/" + id);
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(response, Student.class);
            LOGGER.info("Found student with id " + id);
            return student;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("Get student: " + e.getMessage(), e.getCause());
            throw new ReportException("Error during search current student");
        }
    }

    private List<Mark> getMarksList(int id) throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "marks/student/" + id);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Mark> marks = Arrays.asList(objectMapper.readValue(response, Mark[].class));
            LOGGER.info("Marks list created successfully.\n Have " + marks.size() + " marks");
            return marks;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("Get mark list: " + e.getMessage(), e.getCause());
            throw new ReportException("Error during create marks list");
        }
    }
}
