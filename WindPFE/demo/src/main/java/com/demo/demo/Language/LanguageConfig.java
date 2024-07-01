package com.demo.demo.Language;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Configuration
public class LanguageConfig {

    @Bean
    public LanguageTable languagesTable() {
        Language javaLang1 = new Language("java", "Java", "JDK 9.0.1", "1");
        Language javaLang2 = new Language("java", "Java", "JDK 10.0.1", "2");
        Language nodejsLang = new Language("nodejs", "Node.js", "9.2.0", "1");
        Language python3Lang = new Language("python3", "Python 3", "3", "2");
        Language csharpLang = new Language("csharp", "C#", "mono 5.10.1", "2");
        Language rubyLang = new Language("ruby", "Ruby", "2.2.4", "0");
        Language phpLang1 = new Language("php", "PHP", "5.6.16", "0");
        Language phpLang2 = new Language("php", "PHP", "7.2.5", "2");

        Map.Entry<String, List<Language>> javaEntry = Map.entry("java", Arrays.asList(javaLang1, javaLang2));
        Map.Entry<String, List<Language>> nodejsEntry = Map.entry("nodejs", Arrays.asList(nodejsLang));
        Map.Entry<String, List<Language>> python3Entry = Map.entry("python3", Arrays.asList(python3Lang));
        Map.Entry<String, List<Language>> csharpEntry = Map.entry("csharp", Arrays.asList(csharpLang));
        Map.Entry<String, List<Language>> rubyEntry = Map.entry("ruby", Arrays.asList(rubyLang));
        Map.Entry<String, List<Language>> phpEntry = Map.entry("php", Arrays.asList(phpLang1, phpLang2));

        LanguageTable languageTable = new LanguageTable(Arrays.asList(javaEntry, nodejsEntry, python3Entry, csharpEntry, rubyEntry, phpEntry));

        return languageTable;
    }
}
