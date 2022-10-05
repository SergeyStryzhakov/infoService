package com.lab3.info.util;

import com.lab3.info.exception.ReportException;
import org.apache.coyote.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {
    public static String requestToApiGetMethod(String uri) throws IOException, InterruptedException, ReportException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (httpResponse.statusCode() != 200)
            throw new ReportException("Error during request on " + uri);
        return httpResponse.body();

    }
}
