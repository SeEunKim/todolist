package com.example.todolist.web.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class KakaoRestApi {
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
        System.out.println(returnNode);
        return returnNode;
    }
}