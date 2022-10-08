package com.lab3.info.util;

import com.lab3.info.exception.ReportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {
    /**
     * Create request to remote API
     *
     * @param uri API url
     * @return data in JSON
     * @throws IOException          IOStream exception
     * @throws InterruptedException Thread exception
     * @throws ReportException      Common exception for front controller
     */
    public static String requestToApiGetMethod(String uri)
            throws IOException, InterruptedException, ReportException {
        final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (httpResponse.statusCode() != 200) {
            LOGGER.error("Status code on request " + uri + " => " + httpResponse.statusCode());
            throw new ReportException("Error during request on " + uri);
        }
        return httpResponse.body();

    }
}
