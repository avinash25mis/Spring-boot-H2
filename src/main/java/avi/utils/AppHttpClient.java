package avi.utils;

import avi.dto.response.GenericResponse;
import org.apache.commons.collections4.MapUtils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Component
public class AppHttpClient {


    protected HttpClient httpClient;

    @PostConstruct
    public void getHttpClient(){
        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000));
        httpClientBuilder.version(HttpClient.Version.HTTP_2);
        httpClientBuilder.cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE));
        httpClient = httpClientBuilder.build();
    }



    HttpResponse<String> sendRequest(HttpRequest httpRequest) throws InterruptedException, IOException {
        HttpResponse<String> response = null;
            try {
                response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            }catch (IOException e) {

            }
        return response;
    }


    public GenericResponse getCall(String targetUrl, Map<String, String> headers)  {
        GenericResponse genericResponse= null;
        try {
            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().GET().uri(URI.create(targetUrl.trim()))
                    .timeout(Duration.ofMillis(5000));
            if (MapUtils.isNotEmpty(headers)) {
                headers.forEach(httpRequestBuilder::setHeader);
            }
            HttpRequest httpRequest = httpRequestBuilder.build();
            HttpResponse<String> response = sendRequest(httpRequest);
            genericResponse = new GenericResponse(response.statusCode(), response.body());
        }catch (Exception e){
           genericResponse = new GenericResponse(500, e.getMessage());
        }
        return genericResponse;
    }



}
