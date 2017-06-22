package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("index.html").getFile());
        return new String(Files.readAllBytes(file.toPath()), "UTF-8");
    }

}
