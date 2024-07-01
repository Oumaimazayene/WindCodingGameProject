package com.demo.demo.Language;

import com.demo.demo.config.Entity.JdoodleResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RequestHandler {

    public static final String JDOODLE_ENDPOINT = "https://api.jdoodle.com/v1/execute";

    @Value("${dev.JDOODLE_CLIENT_ID}")
    private String clientId;

    @Value("${dev.JDOODLE_CLIENT_SECRET}")
    private String clientSecret;


    public static String escapeChars(String str) {
        char[][] escapes = {
                { '\n', 'n' },
                { '\r', 'r' },
                { '\f', 'f' },
                { '\b', 'b' },
                { '\t', 't' }
        };
        for (char[] escape : escapes) {
            str = str.replaceAll(Character.toString(escape[0]), "\\\\" + escape[1]);
        }
        return str;
    }

    public JdoodleResponseBody postRunRequest(String language, String version, String script, String stdin) {
        if (clientId == null || clientId.isEmpty() || clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException("ClientId ou ClientSecret n'est pas configuré.");
        }
        script=script.replaceAll("\"","\\\\\"");
        script = escapeChars(script);
        String requestBody = "{\"clientId\":\"" + clientId + "\",\"clientSecret\":\"" + clientSecret + "\",\"script\":\"" + script +
                "\",\"language\":\"" + language + "\",\"versionIndex\":\"" + version + "\",\"stdin\":\"" + stdin + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JdoodleResponseBody> response = restTemplate.exchange(JDOODLE_ENDPOINT, HttpMethod.POST, entity, JdoodleResponseBody.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }


}
