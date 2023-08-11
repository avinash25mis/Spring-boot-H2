package avi.utils;

import avi.configuration.AppLogger;
import avi.dto.response.AppResponse;
import avi.service.PartitionAndExecutionService;
import org.apache.commons.collections4.MapUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;


@Component
public class AppHttpClient {
    Logger logger = LoggerFactory.getLogger(AppHttpClient.class);
    private HttpClient httpClient;
    private int connectTimeout=9000;
    private int connectionRequestTimeout=9000;

    @PostConstruct
    public void getHttpClient(){
        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(connectTimeout));
        httpClientBuilder.version(HttpClient.Version.HTTP_2);
        httpClientBuilder.cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE));
        httpClient = httpClientBuilder.build();
    }



    public AppResponse makeGetCall(String targetUrl, Map<String, String> headers,int callCount)  {
        logger.error("calling targetUrl: "+callCount+"."+targetUrl);
        AppResponse appResponse = null;
        try {
            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().GET().uri(URI.create(targetUrl.trim()))
                    .timeout(Duration.ofMillis(connectionRequestTimeout));
            if (MapUtils.isNotEmpty(headers)) {
                headers.forEach(httpRequestBuilder::setHeader);
            }
            HttpRequest httpRequest = httpRequestBuilder.build();
            HttpResponse<String> response = sendRequest(httpRequest);
            appResponse = new AppResponse(response.statusCode(),callCount+"."+targetUrl,null, response.body());
        }catch (Exception e){
           appResponse = new AppResponse(500,callCount+"."+targetUrl,"exception", e.getMessage());
        }
        return appResponse;
    }


    private HttpResponse<String> sendRequest(HttpRequest httpRequest) throws InterruptedException, IOException {
        HttpResponse<String> response = response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response;
    }



}
