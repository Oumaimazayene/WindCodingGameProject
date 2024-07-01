package com.demo.demo.entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "User", url = "http://localhost:8090/api/recrutteur")
public interface UserClient {
    @GetMapping("/{uuid}")
    User getUserByUUID(@PathVariable("uuid") UUID uuid);
}
