package com.demo.demo.ServiceImpl;

import com.demo.demo.Service.CompilateurService;
import com.demo.demo.config.Entity.ClientRunRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class CompilateurServiceImpl implements CompilateurService {

private  final  RestTemplate restTemplate;
    public boolean compilerCode(ClientRunRequestBody requestBody) {
        String apiUrl = "http://localhost:8070/app/run";

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, requestBody, Map.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}
