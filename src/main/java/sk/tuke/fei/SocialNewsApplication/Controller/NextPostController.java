package sk.tuke.fei.SocialNewsApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.fei.SocialNewsApplication.Model.NextPost;
import sk.tuke.fei.SocialNewsApplication.config.ContentManager;

/**
 * Created by Jaro on 09.10.2017.
 */
@RestController
@RequestMapping("/nextpost")
public class NextPostController {

    @Autowired
    private ContentManager contentManager;

    @GetMapping
    public NextPost getNextPost() {
        NextPost np = contentManager.getNextPost();
        int i = 10;
        while (np == null && i >0 ){
            i--;
            np = contentManager.getNextPost();
        }
        if (i == 0 ){
            NextPost errorNp = new NextPost();
            errorNp.setPostType("Error");
            return errorNp;
        }
        else return np;
    }
}
