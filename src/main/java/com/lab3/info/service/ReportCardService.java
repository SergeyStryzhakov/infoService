package com.lab3.info.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.entity.Mark;
import com.lab3.info.entity.Report;
import com.lab3.info.entity.Student;
import com.lab3.info.entity.Subject;
import com.lab3.info.exception.ReportException;
import com.lab3.info.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReportCardService {
    @Value("${api.url}")
    private String API_URL;

    public ReportCardDto createDtoById(int id) throws ReportException {
        Student student = getStudent(id);
        return new ReportCardDto.Builder()
                .studentName(student.getFirstName() + " " + student.getLastName())
                .studentGroup(student.getGroupName())
                .reports(createReportList(id))
                .build();
    }

    public List<Student> getStudents() throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "students");
            if(response.isEmpty()) {
                throw  new ReportException("No student found, check URL");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(response, Student[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
            return Arrays.asList(objectMapper.readValue(response, Subject[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ReportException("Error during create subject list");
        }
    }

    private Student getStudent(int id) throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "students/" + id);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, Student.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ReportException("Error during search current student");
        }
    }

    private List<Mark> getMarksList(int id) throws ReportException {
        try {
            String response = HttpUtils.requestToApiGetMethod(API_URL + "marks/student/" + id);
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(response, Mark[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ReportException("Error during create marks list");
        }
    }
}
