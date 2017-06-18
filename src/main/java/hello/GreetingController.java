package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        jdbcTemplate.execute("UPDATE COUNTER SET counter = counter + 1 WHERE ID = '1';");
        Long counter = jdbcTemplate.queryForObject("SELECT counter FROM COUNTER WHERE ID ='1';", Long.class);
        return new Greeting(counter,
                            String.format(template, name));
    }
}
