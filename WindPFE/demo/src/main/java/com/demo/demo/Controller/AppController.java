package com.demo.demo.Controller;



import com.demo.demo.Language.LanguagesManager;
import com.demo.demo.Language.RequestHandler;
import com.demo.demo.config.Entity.ClientRunRequestBody;
import com.demo.demo.config.Entity.JdoodleResponseBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/app")
public class AppController {

    private final LanguagesManager languagesManager;
    private final RequestHandler requestHandler;

    @GetMapping("/langs")
    public ResponseEntity<Map<String, Object>> getLangs() {
        Map<String, Object> response = new HashMap<>();
        response.put("langs", languagesManager.getLanguagesMap());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/run")
    public ResponseEntity<JdoodleResponseBody> postRunRequest(@RequestBody ClientRunRequestBody body) {
        if (!validatePostRun(body)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            String index = languagesManager.getLanguageVersionIndex(body.getLanguage(), body.getVersion());

            JdoodleResponseBody result = requestHandler.postRunRequest(body.getLanguage(), index, body.getScript(), body.getStdin());
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validatePostRun(ClientRunRequestBody reqBody) {
        return reqBody != null &&
                reqBody.getLanguage() != null &&
                reqBody.getVersion() != null &&
                reqBody.getScript() != null &&
                !reqBody.getScript().isEmpty() &&
                languagesManager.isLangSupported(reqBody.getLanguage(), reqBody.getVersion());
    }
}