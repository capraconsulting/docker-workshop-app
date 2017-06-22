package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RestController
public class HealthController {

    @RequestMapping("/health")
    public Map<String, String> health() throws UnknownHostException {
        Map<String, String> res = new HashMap<>();

        res.put("response", String.format("Ok from %s", InetAddress.getLocalHost().getHostName()));
        res.put("startTime",
                ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                                ManagementFactory.getRuntimeMXBean().getStartTime()
                        ),
                        TimeZone.getTimeZone("UTC").toZoneId()
                ).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        return res;
    }
}
