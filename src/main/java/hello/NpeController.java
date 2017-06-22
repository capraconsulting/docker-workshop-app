package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NpeController {
    private static final Logger log = LoggerFactory.getLogger(NpeController.class);

    @RequestMapping("/npe")
    public String fail() {
        // Simulate the JVM crashing by halting the application
        throw new NullPointerException("Something was not properly nullchecked!!");
    }
}
