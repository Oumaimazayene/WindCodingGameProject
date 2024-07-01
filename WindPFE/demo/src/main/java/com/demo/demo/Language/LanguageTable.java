package com.demo.demo.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Data
public class LanguageTable {
    private List<Map.Entry<String, List<Language>>> entries;

    public List<Map.Entry<String, List<Language>>> getEntries() {
        return entries;
    }

    public void setEntries(List<Map.Entry<String, List<Language>>> entries) {
        this.entries = entries;
    }
}