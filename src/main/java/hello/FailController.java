package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailController {
    private static final Logger log = LoggerFactory.getLogger(FailController.class);

    @RequestMapping("/fail")
    public String fail() {
        // Simulate the JVM crashing by halting the application
        log.error("The workshop app crashed!");
        Runtime.getRuntime().halt(1);

        return null;
    }
}
