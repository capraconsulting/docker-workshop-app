package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:haakon@nymomatland.com">Håkon Nymo Matland</a> 2017-06-18.
 */
@RestController
public class ExceptionTriggerController {
    @RequestMapping("/fail")
    public String fail() {
        throw new NullPointerException("NPE!");
    }
}
