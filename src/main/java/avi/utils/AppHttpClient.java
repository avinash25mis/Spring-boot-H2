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
    protected HttpClient httpClient;



    @PostConstruct
    public void getHttpClient(){
        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(5000));
        httpClientBuilder.version(HttpClient.Version.HTTP_2);
        httpClientBuilder.cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE));
        httpClient = httpClientBuilder.build();
    }








    public AppResponse makeGetCall(String targetUrl, Map<String, String> headers)  {
        logger.error("calling targetUrl: "+targetUrl);
        AppResponse appResponse = null;
        try {
            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().GET().uri(URI.create(targetUrl.trim()))
                    .timeout(Duration.ofMillis(5000));
            if (MapUtils.isNotEmpty(headers)) {
                headers.forEach(httpRequestBuilder::setHeader);
            }
            HttpRequest httpRequest = httpRequestBuilder.build();
            HttpResponse<String> response = sendRequest(httpRequest);
            appResponse = new AppResponse(response.statusCode(),targetUrl,null, response.body());
        }catch (Exception e){
           appResponse = new AppResponse(500,targetUrl,"exception", e.getMessage());
        }
        return appResponse;
    }


    private HttpResponse<String> sendRequest(HttpRequest httpRequest) throws InterruptedException, IOException {
        HttpResponse<String> response = response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response;
    }



}
