package hello;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() throws IOException {

        return StreamUtils.copyToString(
            getClass().getClassLoader().getResourceAsStream("index.html"),
            Charset.forName("UTF-8")
        );
    }

}
