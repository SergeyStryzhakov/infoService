package com.lab3.info.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDTO;
import com.lab3.info.entity.Mark;
import com.lab3.info.entity.Report;
import com.lab3.info.entity.Student;
import com.lab3.info.entity.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class CreateReportCardService {
    @Value("${api.url}")
    private String API_URL;


    public ReportCardDTO createDtoById(int id) throws IOException, InterruptedException {
        Student student = getStudent(id);
        return new ReportCardDTO.Builder()
                .studentName(student.getFirstName() + " " + student.getLastName())
                .studentGroup(student.getGroupName())
                .reports(createReportList(id))
                .build();


    }

    private List<Report> createReportList(int id) throws IOException, InterruptedException {
        Map<String, ArrayList<Mark>> subjectMarks = new HashMap<>();
        List<Report> reports = new ArrayList<>();
        List<Mark> marks = getMarksList(id);
        List<Subject> subjects = getSubjectList();

        for (Subject subject : subjects) {
            subjectMarks.put(subject.getTitle(), new ArrayList<>());
        }

        Map<String, String> report = new HashMap<>();

        for (Mark mark : marks) {
            subjectMarks.get(mark.getSubject().getTitle()).add(mark);
        }
        double averageMark;

        for (String subject : subjectMarks.keySet()) {
            averageMark = subjectMarks.get(subject)
                    .stream()
                    .mapToInt(Mark::getValue)
                    .average()
                    .orElse(0.00);
            report.put(subject, String.format("%.2f", averageMark));
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

    private List<Subject> getSubjectList() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(API_URL + "subjects")).GET().build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String resp = httpResponse.body();
        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.asList(objectMapper.readValue(resp, Subject[].class));
    }

    private Student getStudent(int id) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(API_URL + "students/" + id)).GET().build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String resp = httpResponse.body();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(resp, Student.class);
    }

    private List<Mark> getMarksList(int id) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "marks/student/" + id))
                .GET()
                .build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String resp = httpResponse.body();
        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.asList(objectMapper.readValue(resp, Mark[].class));
    }
}
