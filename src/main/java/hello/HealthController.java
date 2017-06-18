package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:haakon@nymomatland.com">Håkon Nymo Matland</a> 2017-06-18.
 */
@RestController
public class HealthController {

    @RequestMapping("/health")
    public Map<String,String> health() throws UnknownHostException {
        return Collections.singletonMap("response", String.format("Ok from %s", InetAddress.getLocalHost().getHostName()));
    }
}
