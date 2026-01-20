package org.banana.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.docmind_api20220711.Client;
import com.aliyun.docmind_api20220711.models.*;
import com.aliyun.teaopenapi.models.Config;

import java.util.List;
import java.util.Map;

public class OCRClient2 {
    public static void main(String[] args) throws Exception {

        WebClient.builder()
                .baseUrl("https://api.deepseek.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

    }
    public static String submit(Config config) throws Exception {
        Client client = new Client(config);
        // 调用接口时，程序直接访问凭证，读取您的访问密钥（即AccessKey）并自动完成鉴权。
        // 运行本示例前，请先完成步骤二：配置身份认证。
        // 本示例使用默认配置文件方式，通过配置Credentials文件创建默认的访问凭证。
        // 使用默认凭证初始化Credentials Client。
        // 替换成具体异步任务提交类API接口的入参和方法，示例方法是文档智能解析。
        SubmitDocStructureJobRequest request = new SubmitDocStructureJobRequest();
        request.fileName = "1.pdf";
        request.fileUrl = "https://p9-bot-workflow-sign.byteimg.com/tos-cn-i-mdko3gqilj/306cb0d0947344ac94d4d16395740be9.pdf~tplv-mdko3gqilj-image.image?rk3s=81d4c505&x-expires=1782962795&x-signature=69tYt6vcC55xHw%2BzMwb1PfxGGaQ%3D&x-wf-file_name=1.+%E3%80%90%E7%94%B5%E5%AD%90%E7%AD%BE%E8%AF%81%E3%80%91E-VISA+Wan+Ailin+PT+CLOUDUN+TECHNOLOGY+INDONESIA.pdf%22,%22https://p9-bot-workflow-sign.byteimg.com/tos-cn-i-mdko3gqilj/9b99fff9934d424096fe146d5e8d7bc4.pdf~tplv-mdko3gqilj-image.image?rk3s=81d4c505&x-expires=1782962797&x-signature=32Rlxt5VNnzHfvi6wly0HpmLKKk%3D&x-wf-file_name=1.+%E3%80%90%E7%94%B5%E5%AD%90%E7%AD%BE%E8%AF%81%E3%80%91E-VISA+Gong+Lin+PT+CLOUDUN+TECHNOLOGY+INDONESIA.pdf";
        SubmitDocStructureJobResponse response = client.submitDocStructureJob(request);
        System.out.println(JSON.toJSON(response.getBody()));
        return response.getBody().getData().getId();
    }

    public  static GetDocStructureResultResponseBody query(String requestId, Config config) throws Exception {
        Client client = new Client(config);
        GetDocStructureResultRequest resultRequest = new GetDocStructureResultRequest();
        resultRequest.id = requestId;
        resultRequest.setRevealMarkdown(true);
        GetDocStructureResultResponse response = client.getDocStructureResult(resultRequest);
        System.out.println(JSON.toJSON(response.getBody()));
        return response.getBody();
    }
}