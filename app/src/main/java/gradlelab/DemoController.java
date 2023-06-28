package gradlelab;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public ResponseEntity<String> helloWorld(@RequestParam(value = "name", defaultValue = "Worldx") String name) {
        return ResponseEntity.ok(String.format("Hello %s!", name));
    }
}