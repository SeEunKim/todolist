package com.example.todolist.web;

import com.example.todolist.web.login.KakaoRestApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = getLogger(RestTemplateResponseErrorHandler.class);
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR ;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        logger.debug("## handleError : {}",  response);
        if (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

        } else if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            logger.debug("## handleError : {}",  response.getBody());
            logger.debug("## handleError : {}",  "여기서 권한 요청을 한다.");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            KakaoRestApi kakaoRestApi = new KakaoRestApi();
            kakaoRestApi.requestScopeForKakao(node);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                //throw new NotFoundException();
            }
        }
    }
}
