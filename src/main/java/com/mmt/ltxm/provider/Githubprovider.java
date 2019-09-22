package com.mmt.ltxm.provider;

import com.alibaba.fastjson.JSON;
import com.mmt.ltxm.dto.AccessTokoenDTO;
import com.mmt.ltxm.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.alibaba.fastjson.JSON.toJSONString;

@Component
public class Githubprovider {
    public String getAccessToken(AccessTokoenDTO accessTokoenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, toJSONString(accessTokoenDTO));
        Request request = new Request.Builder()
                .url("http://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
