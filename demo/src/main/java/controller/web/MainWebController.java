package controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}