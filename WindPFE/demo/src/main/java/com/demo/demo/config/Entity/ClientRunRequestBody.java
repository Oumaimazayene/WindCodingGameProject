package com.demo.demo.config.Entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Text;


@Data
@AllArgsConstructor
public class ClientRunRequestBody {

    private String language;
    private String version;
    @JsonValue
    private String script;
    private String stdin;
}