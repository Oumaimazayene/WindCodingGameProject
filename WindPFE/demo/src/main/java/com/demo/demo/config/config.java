package com.demo.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
@Configuration
public class config {

        private final String env;

        public config() {
            this.env = System.getProperty("NODE_ENV", "dev");
        }

        @PostConstruct
        public void setup() {
            assignConfig();
        }

        private void assignConfig() {
            if ("dev".equals(env) || "test".equals(env)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try (InputStream input = getClass().getClassLoader().getResourceAsStream("/.config.json")) {
                    if (input == null) {
                        System.out.println("Sorry, unable to find config.json");
                        return;
                    }

                    // Load JSON content into a Map
                    Map<String, Map<String, String>> configMap = objectMapper.readValue(input, Map.class);

                    // Get the environment-specific properties
                    Map<String, String> envConfig = configMap.get(env);

                    // Set system properties
                    if (envConfig != null) {
                        envConfig.forEach((key, value) -> System.setProperty(key, value));
                    } else {
                        System.out.println("Environment-specific config not found for: " + env);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
