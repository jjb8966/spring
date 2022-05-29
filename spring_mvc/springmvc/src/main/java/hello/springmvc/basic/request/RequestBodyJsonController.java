package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("String Data : {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("Object Data : {}", helloData);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("Object Data : {}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("Object Data : {}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v4")
    public HelloData requestBodyJsonV4(@RequestBody HelloData helloData) throws IOException {
        helloData.setAge(100);
        log.info("Object Data : {}", helloData);

        return helloData;
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v5")
    public String requestBodyJsonV5(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        log.info("Object Data : {}", helloData);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v6")
    public HttpEntity<HelloData> requestBodyJsonV6(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        helloData.setAge(100);
        log.info("Object Data : {}", helloData);

        return new HttpEntity<HelloData>(helloData);
    }

    @ResponseBody
    @RequestMapping("/request-body-json-v7")
    public HelloData requestBodyJsonV7(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        helloData.setAge(100);
        log.info("Object Data : {}", helloData);

        return helloData;
    }
}
