package sk.tuke.fei.SocialNewsApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sk.tuke.fei.SocialNewsApplication.Model.NextPost;
import sk.tuke.fei.SocialNewsApplication.Model.Votes;
import sk.tuke.fei.SocialNewsApplication.Model.VotesDao;
import sk.tuke.fei.SocialNewsApplication.Model.VotesResult;
import sk.tuke.fei.SocialNewsApplication.config.ContentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaro on 15.10.2017.
 */
@RestController
public class VotingController {

    @Autowired
    private ContentManager contentManager;
    @Autowired
    private VotesDao votesDao;

    @PostMapping("/vote")
    public ResponseEntity<String> saveRating(@RequestBody String p) {
        Boolean result = liked(p);
        System.out.println(p);
        if (result == null) {
            System.out.println("ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (result) {
            System.out.println("LIKED");
            contentManager.voteCurrentPost(true);
        } else {
            System.out.println("DISLIKED");
            contentManager.voteCurrentPost(false);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/vote")
    public ModelAndView getPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("voting");
        return model;
    }

    @GetMapping("/getCurrent")
    public NextPost getCurrentTitle() {
        return contentManager.getCurrentPost();
    }

    private Boolean liked(String p) {
        System.out.println("Respond: " + p);
        String[] votes = p.split("&");
        if (votes[0].equals("liked=1")) return true;
        if (votes[1].equals("disliked=1")) return false;
        else return null;
    }

    @GetMapping("/getVotes")
    public Iterable<VotesResult> getAllVotes() {
        Iterable<Votes> votes = votesDao.findAll();
        HashMap<String, VotesResult> map = new HashMap<>();
        for (Votes vote : votes) {
            if (!map.containsKey(vote.getPost_id())) {
                VotesResult v = new VotesResult();
                v.setPostType(vote.getPostType());
                v.setPostId(vote.getPost_id());
                if (vote.isLiked()) {
                    v.setLiked(1);
                    v.setDisliked(0);
                } else {
                    v.setDisliked(1);
                    v.setLiked(0);
                }
                map.put(vote.getPost_id(), v);
            } else {
                if (vote.isLiked()) map.get(vote.getPost_id()).addLike();
                else map.get(vote.getPost_id()).addDislike();
            }
        }
        List<VotesResult> votesResults = new ArrayList<>(map.values());
        return votesResults;
    }


}
