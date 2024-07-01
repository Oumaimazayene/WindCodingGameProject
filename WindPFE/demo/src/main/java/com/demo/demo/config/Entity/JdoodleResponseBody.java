package com.demo.demo.config.Entity;

import lombok.Data;

@Data
public class JdoodleResponseBody {
    private String output;
    private Integer statusCode;
    private String memory;
    private String cpuTime;
}
