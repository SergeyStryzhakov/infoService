package com.lab3.info.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.entity.Mark;
import com.lab3.info.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class InfoService {

    @Value("${api.url}")
    private String API_URL;

    public List<Student> getStudents() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newBuilder().build();
        //HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json")).GET().build();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL + "students"))
                .GET()
                .build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String resp = httpResponse.body();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Student> students = Arrays.asList(objectMapper.readValue(resp, Student[].class));
               return students;
    }


}
