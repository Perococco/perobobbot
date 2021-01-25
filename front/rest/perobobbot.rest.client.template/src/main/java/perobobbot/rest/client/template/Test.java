package perobobbot.rest.client.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import perobobbot.rest.client.SecurityClient;
import perobobbot.security.com.Credential;

public class Test {

    public static void main(String[] args) {
        testRest();
    }

    public static void testRest() {
        final ObjectMapper mapper = new ObjectMapper().registerModule(new GuavaModule());
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://localhost:8443/api"));

        restTemplate.getMessageConverters().removeIf(i -> i instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(0,new MappingJackson2HttpMessageConverter(mapper));

        final SecurityClient securityClient = new SecurityClientTemplate(restTemplate);

        final var jwtInfo = securityClient.signIn(new Credential("perococco","admin"));


        System.out.println(jwtInfo);


    }
}
