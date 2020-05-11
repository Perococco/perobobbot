package bot.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping(value = "/cities")
    public List<String> getCities() {

        List<String> cities = new ArrayList<>();
        cities.add("Hello");
        cities.add("World");

        return cities;
    }
}
