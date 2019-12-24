package com.ruihe.community.provider;

import com.alibaba.fastjson.JSON;
import com.ruihe.community.dto.AccessTokenDTO;
import com.ruihe.community.dto.GithubUserDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO dto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            log.error("getAccessToken error,{}", dto, e);
        }
        return null;
    }

    public GithubUserDTO getUser(String access_token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + access_token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUserDTO user = JSON.parseObject(string, GithubUserDTO.class);
//            System.out.println(user);
            return user;
        } catch (Exception e) {
            log.error("getUser error,{}", access_token, e);
        }
        return null;
    }
}