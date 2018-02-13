package sk.tuke.fei.SocialNewsApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sk.tuke.fei.SocialNewsApplication.Model.NextPost;
import sk.tuke.fei.SocialNewsApplication.config.ContentManager;

/**
 * Created by Jaro on 15.10.2017.
 */
@RestController
public class VotingController {

    @Autowired
    private ContentManager contentManager;

    @PostMapping("/vote")
    public ResponseEntity< String > saveRating(@RequestBody String p) {
        Boolean result = liked(p);
        System.out.println(p);
        if (result==null) {
            System.out.println("ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (result){
            System.out.println("LIKED");
            contentManager.voteCurrentPost(true);
        }
        else {
            System.out.println("DISLIKED");
            contentManager.voteCurrentPost(false);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/vote")
    public ModelAndView getPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("voting");
        return model;
    }

    @GetMapping("/getCurrent")
    public NextPost getCurrentTitle(){
        return contentManager.getCurrentPost();
    }

    private Boolean liked(String p) {
        System.out.println("Respond: "+p);
        String[] votes = p.split("&");
        if (votes[0].equals("liked=1")) return true;
        if (votes[1].equals("disliked=1")) return false;
        else return null;
    }
}
