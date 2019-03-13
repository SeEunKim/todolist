package com.example.todolist.web.login;

import com.example.todolist.web.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class KakaoRestApi {
    private static final Logger logger = getLogger(KakaoRestApi.class);


    public JsonNode getAccessToken(String autorize_code) {
        final String requestUrl = "https://kauth.kakao.com/oauth/token";
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9c6c046b932d7910776ca22b31d9b3bb");
        params.add("redirect_uri", "http://localhost:8080/kakao_oauth");
        params.add("code", autorize_code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);


        JsonNode returnNode = null;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnNode = mapper.readTree(String.valueOf(response.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("## getAccessToken : {}",  returnNode);
        return returnNode;
    }

    /**
     * GET/POST /v2/user/me HTTP/1.1
     * Host: kapi.kakao.com
     * Authorization: Bearer {access_token}
     * Content-type: application/x-www-form-urlencoded;charset=utf-8
     * @param access_token
     * @return
     */
    public JsonNode getUserInfo(String access_token) {
        final String requestUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + access_token);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        JsonNode returnNode = null;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnNode = mapper.readTree(String.valueOf(response.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("## getUserInfo : {}",  returnNode);
        return returnNode;
    }

    public JsonNode logout(String access_token) {
        final String requestUrl = "https://kapi.kakao.com/v1/user/logout";
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        //Authorization: Bearer {access_token}
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + access_token);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        JsonNode returnNode = null;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnNode = mapper.readTree(String.valueOf(response.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("## logout : {}",  returnNode);
        return returnNode;
    }

    //

    public JsonNode sendMessage(String access_token) {
        final String requestUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "title");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + access_token);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        JsonNode returnNode = null;

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();

        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
        logger.debug("## sendMessage : {}",  response.getStatusCode());
        logger.debug("## sendMessage : {}",  response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnNode = mapper.readTree(String.valueOf(response.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnNode;
    }

    public String requestScopeForKakao(JsonNode node) {
        String requestScope = node.get("required_scopes").toString();
        String requesta = requestScope.substring(2, requestScope.length() - 2);
        System.out.println(requesta);

        final String requestUrl = "https://kauth.kakao.com/";
        String requestBody = "/oauth/authorize?client_id=9c6c046b932d7910776ca22b31d9b3bb&redirect_uri=http://localhost:8080/kakao_oauth&response_type=code&scope=" + requesta;

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl+requestBody, String.class);
        logger.debug("## requestScopeForKakao : {}",  response.getBody());

        return "redirect:/";

    }
}