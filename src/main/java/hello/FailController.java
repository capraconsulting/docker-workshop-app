package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailController {
    @RequestMapping("/fail")
    public String fail() {
        // Simulate the JVM crashing by halting the application
        Runtime.getRuntime().halt(1);

        return null;
    }
}
