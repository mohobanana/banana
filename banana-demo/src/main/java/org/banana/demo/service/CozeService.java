package org.banana.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.RunWorkflowResp;
import com.coze.openapi.service.auth.JWTOAuthClient;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.banana.demo.entity.GradeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class CozeService {

    @Value("${coze.clientId}")
    private String clientId;
    @Value("${coze.jwt.publicKey}")
    private String publicKey;
    @Value("${coze.jwt.oauthUrl}")
    private String oauthUrl;
    @Value("${coze.workflowUrl}")
    private String workflowUrl;
    private String privateKey;

    @PostConstruct
    public void init(){
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("private_key.pem")){
            if (inputStream == null) {
                throw new RuntimeException("coze:获取私钥失败");
            }
            this.privateKey = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }catch (IOException e){
            throw new RuntimeException("coze:读取私钥失败",e);
        }
    }

    public String getToken(){
        JWTOAuthClient oauth = null;
        try {
            oauth = new JWTOAuthClient.JWTOAuthBuilder()
                    .clientID(clientId)
                    .privateKey(privateKey)
                    .publicKey(publicKey)
                    .baseURL(oauthUrl)
                    .jwtBuilder(new MyJWTBuilder())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        OAuthToken accessToken = oauth.getAccessToken();
        System.out.println(accessToken);
        return accessToken.getAccessToken();
    }

    public String getGrade(String companyName){
        TokenAuth authCli = new TokenAuth(getToken());
        CozeAPI coze =
                new CozeAPI.Builder()
                        .baseURL(workflowUrl)
                        .auth(authCli)
                        .readTimeout(20000)
                        .build();
        ;

        String workflowID = "7509742888801042471";
        Map<String, Object> data = new HashMap<>();
        data.put("companyName", companyName);
        RunWorkflowReq req = RunWorkflowReq.builder().workflowID(workflowID).parameters(data).build();

        RunWorkflowResp resp = coze.workflows().runs().create(req);
        Gson gson = new Gson();
        GradeDto gradeDto = gson.fromJson(resp.getData(), GradeDto.class);
        String s = gson.toJson(gradeDto);
        System.out.println(s);
        return s;
    }




}
