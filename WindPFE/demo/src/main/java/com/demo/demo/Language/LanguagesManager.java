package com.demo.demo.Language;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component

public class LanguagesManager {
    private static final LanguageTable languageTable = new LanguageConfig().languagesTable();
    private static final Map<String, List<Language>> languagesMap = languageTable.getEntries().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    public static String getLanguageVersionIndex(String lang, String version) {
        if (isLangSupported(lang, version)) {
            List<Language> langEntries = languagesMap.get(lang);
            Language entry = langEntries.stream().filter(l -> l.getVersion().equals(version)).findFirst().orElse(null);
            if (entry != null) {
                return entry.getIndex();
            }
        }
        return null;
    }

    public static boolean isLangSupported(String lang, String version) {
        return languagesMap.containsKey(lang) &&
                languagesMap.get(lang).stream().anyMatch(l -> l.getVersion().equals(version));
    }

    public static Map<String, List<Language>> getLanguagesMap() {
        return languagesMap;
    }
}
