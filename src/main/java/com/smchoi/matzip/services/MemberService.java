package com.smchoi.matzip.services;

import com.smchoi.matzip.entities.member.UserEntity;
import com.smchoi.matzip.mappers.IMemberMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service(value = "com.smchoi.matzip.services.MemberService")
public class MemberService {

    private final IMemberMapper memberMapper;

    @Autowired
    public MemberService(IMemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public String getKakaoAccessToken(String code) throws IOException {
        URL url = new URL("https://kauth.kakao.com/oauth/token");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        int responseCode;
        try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream())) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
                StringBuilder requestBuilder = new StringBuilder();
                requestBuilder.append("grant_type=authorization_code");
                requestBuilder.append("&client_id=137cbbb98ac38f2199a20dd210d752d8");
                requestBuilder.append("&redirect_uri=http://localhost:8080/member/kakao");
                requestBuilder.append("&code=").append(code);
                bufferedWriter.write(requestBuilder.toString());
                bufferedWriter.flush();
                responseCode = connection.getResponseCode();
            }
            System.out.println("응답코드 : " + responseCode);
        }
        StringBuilder responseBuilder = new StringBuilder();
        try(InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBuilder.append(line);
                }
            }
            System.out.println("응답 내용 : " + responseBuilder);
        }
        JSONObject responseObject = new JSONObject(responseBuilder.toString());
        return responseObject.getString("access_token");
    }

    public UserEntity getKakaoUserInfo(String accessToken) throws IOException{
        URL url = new URL("https://kapi.kakao.com/v2/user/me");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("응답 코드 : " + responseCode);
        StringBuilder responseBuilder = new StringBuilder();
        try(InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
            try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBuilder.append(line);
                }
            }
        }
        System.out.println("응답내용 : " + responseBuilder);
        JSONObject responseObject = new JSONObject(responseBuilder.toString());
        JSONObject propertyObject = responseObject.getJSONObject("properties");
        System.out.println("닉네임 : " + propertyObject.getString("nickname"));
        String id = String.valueOf(responseObject.getLong("id"));
        UserEntity user = this.memberMapper.selectUserById(id);
        if(user == null) {
            user = new UserEntity();
            user.setId(id);
            user.setNickname(propertyObject.getString("nickname"));
            this.memberMapper.insertUser(user);
        }
        return user;
    }
}
