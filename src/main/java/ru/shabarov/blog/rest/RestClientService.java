package ru.shabarov.blog.rest;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shabarov.blog.entity.Post;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RestClientService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${blog.rest.timeout:10000}")
    private int timeout = 10000;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("RestClientService running");
                Post post = restTemplate.getForObject("http://localhost:8080/rest/post/18", Post.class);
                logger.info("Rest service fetched post = {}", post);
            }
        }, 3, TimeUnit.SECONDS);
    }
}
