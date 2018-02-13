package sk.tuke.fei.SocialNewsApplication.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jaro on 15.10.2017.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String getNextPost() {
        String text = "";
        return text;
    }
}
