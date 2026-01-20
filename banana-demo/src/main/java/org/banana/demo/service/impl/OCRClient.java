package com.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class OCRClient {
    private final String appId;
    private final String secretCode;
    private final String baseUrl;

    public OCRClient(String appId, String secretCode) {
        this.appId = appId;
        this.secretCode = secretCode;
        this.baseUrl = "https://api.textin.com/ai/service/v1/pdf_to_markdown";
    }

    public String recognize(byte[] fileContent, HashMap<String, Object> options) throws IOException {
        // Build URL with query parameters
        StringBuilder queryParams = new StringBuilder();
        // Add query parameters
        for (Map.Entry<String, Object> entry : options.entrySet()) {
            if (queryParams.length() > 0) {
                queryParams.append("&");
            }
            queryParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }

        // Create full URL with query parameters
        String fullUrl = baseUrl + (queryParams.length() > 0 ? "?" + queryParams : "");
        URL url = new URL(fullUrl);

        // Create and configure HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        // Set headers
        connection.setRequestProperty("x-ti-app-id", appId);
        connection.setRequestProperty("x-ti-secret-code", secretCode);
        // 方式一：读取本地文件
//        connection.setRequestProperty("Content-Type", "application/octet-stream");
        // 方式二：使用URL方式
         connection.setRequestProperty("Content-Type", "text/plain");

        // Enable output and send file content
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(fileContent);
            os.flush();
        }

        // Read response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP request failed with code: " + responseCode);
        }
    }

//    public static void main(String[] args) throws IOException {
//        OCRClient client = new OCRClient("34dfda72fdf785e5e813f67675ada70d", "a8852a144703afc11d02eaa47a20bf55");
//
//        // Read image file
//        // 方式一：读取本地文件
////        byte[] fileContent = Files.readAllBytes(Paths.get("example.png"));
//        // 方式二：使用URL方式（需要将headers中的Content-Type改为'text/plain'）
//         byte[] fileContent = "https://p9-bot-workflow-sign.byteimg.com/tos-cn-i-mdko3gqilj/306cb0d0947344ac94d4d16395740be9.pdf~tplv-mdko3gqilj-image.image?rk3s=81d4c505&x-expires=1782962795&x-signature=69tYt6vcC55xHw%2BzMwb1PfxGGaQ%3D&x-wf-file_name=1.+%E3%80%90%E7%94%B5%E5%AD%90%E7%AD%BE%E8%AF%81%E3%80%91E-VISA+Wan+Ailin+PT+CLOUDUN+TECHNOLOGY+INDONESIA.pdf%22,%22https://p9-bot-workflow-sign.byteimg.com/tos-cn-i-mdko3gqilj/9b99fff9934d424096fe146d5e8d7bc4.pdf~tplv-mdko3gqilj-image.image?rk3s=81d4c505&x-expires=1782962797&x-signature=32Rlxt5VNnzHfvi6wly0HpmLKKk%3D&x-wf-file_name=1.+%E3%80%90%E7%94%B5%E5%AD%90%E7%AD%BE%E8%AF%81%E3%80%91E-VISA+Gong+Lin+PT+CLOUDUN+TECHNOLOGY+INDONESIA.pdf".getBytes(StandardCharsets.UTF_8);
//
//        HashMap<String, Object> options = new HashMap<>();
//        options.put("table_flavor","md");
//        options.put("paratext_mode","body");
////        options.put("formula_level","0");
//
//        long start = System.currentTimeMillis();
//
//        try {
//            String response = client.recognize(fileContent, options);
//
//            // 保存完整的JSON响应到result.json文件
//            Files.write(Paths.get("result.json"), response.getBytes());
//
//            // 解析JSON响应以提取markdown内容
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(response);
//            if (jsonNode.has("result") && jsonNode.get("result").has("markdown")) {
//                String markdown = jsonNode.get("result").get("markdown").asText();
//                Files.write(Paths.get("result.md"), markdown.getBytes());
//            }
//
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(System.currentTimeMillis()-start);
//    }

    public static void main(String[] args) {
        System.out.println("\n");
    }
}