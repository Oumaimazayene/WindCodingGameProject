package com.demo.demo.Service;


import com.demo.demo.config.Entity.ClientRunRequestBody;

public interface CompilateurService {
    boolean compilerCode(ClientRunRequestBody requestBody);

}
