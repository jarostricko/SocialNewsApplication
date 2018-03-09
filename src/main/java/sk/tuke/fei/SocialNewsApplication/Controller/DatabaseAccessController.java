package sk.tuke.fei.SocialNewsApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sk.tuke.fei.SocialNewsApplication.Model.*;
import sk.tuke.fei.SocialNewsApplication.config.ContentManager;
import sk.tuke.fei.SocialNewsApplication.config.FacebookContent;

/**
 * Created by Jaro on 01.02.2018.
 */
@RestController
public class DatabaseAccessController {
    @Autowired
    private FaceBookPageDao faceBookPageDao;
    @Autowired
    private YouTubeChannelDao youTubeChannelDao;
    @Autowired
    private CredentialsDao credentialsDao;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private ContentManager contentManager;
    @Autowired
    private FacebookContent facebookContent;

    @GetMapping("/database")
    public ModelAndView getPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("databaseAccess");
        return model;
    }
    @GetMapping("/creds")
    public Credentials getCreds(){
        /*Iterable<Credentials> all = credentialsDao.findAll();
        int size=0;
        if (all instanceof Collection<?>) {
            size = ((Collection<?>)all).size();
        }
        if (size > 1){
            return
        }*/
        return credentialsDao.findOne(1);
    }

    @GetMapping("/ytchannel")
    public Iterable<YouTubeChannel> getListOfYTChannels(){
        return youTubeChannelDao.findAll();
    }

    @GetMapping("/fbpage")
    public Iterable<FaceBookPage> getListOfFBPages(){
        return faceBookPageDao.findAll();
    }

    @GetMapping("/config")
    public Config getConfig(){
        return configDao.findOne(1);
    }

    @PostMapping("/config")
    @ResponseBody
    public ResponseEntity< String > updateConf(@RequestBody Config conf) {
        if (conf==null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Config c = new Config();
        c.setId(1);
        c.setPostTime(conf.getPostTime());
        c.setFacebookPostsOn(conf.isFacebookPostsOn());
        c.setFacebookVideosOn(conf.isFacebookVideosOn());
        c.setFbNumberOfPostsToFetch(conf.getFbNumberOfPostsToFetch());
        c.setMostPopularFirst(conf.isMostPopularFirst());
        c.setNewestFirst(conf.isNewestFirst());
        c.setOldMaxDays(conf.getOldMaxDays());
        c.setVideoOrder(conf.getVideoOrder());
        c.setYoutubeBasedOnChannelsOn(conf.isYoutubeBasedOnChannelsOn());
        c.setYoutubeBasedOnQueryTermsOn(conf.isYoutubeBasedOnQueryTermsOn());
        c.setYtNumberOfPostsToFetch(conf.getYtNumberOfPostsToFetch());
        configDao.save(c);
        contentManager.setConfigs();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/ytchannel")
    @ResponseBody
    public ResponseEntity< String > updateYtChannel(@RequestBody YouTubeChannel ytch) {
        if (ytch==null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        YouTubeChannel channel = youTubeChannelDao.findOne(ytch.getId());
        channel.setActive(ytch.isActive());
        channel.setChannelId(ytch.getChannelId());
        channel.setChannelName(ytch.getChannelName());
        channel.setKeywords(ytch.getKeywords());
        youTubeChannelDao.save(channel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/creds")
    @ResponseBody
    public ResponseEntity< String > updateCreds(@RequestBody Credentials creds) {
        if (creds==null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Credentials c = new Credentials();
        c.setId(1);
        c.setFbAppId(creds.getFbAppId());
        c.setFbAppSecret(creds.getFbAppSecret());
        c.setYouTubeApiKey(creds.getYouTubeApiKey());
        credentialsDao.save(c);
        facebookContent.setCredentials();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/fbpage")
    @ResponseBody
    public ResponseEntity< String > updateFbPage(@RequestBody FaceBookPage p) {
        if (p==null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        FaceBookPage fbp = faceBookPageDao.findOne(p.getId());
        fbp.setActive(p.isActive());
        fbp.setPageId(p.getPageId());
        fbp.setPageName(p.getPageName());
        faceBookPageDao.save(fbp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/fbpage")
    @ResponseBody
    public ResponseEntity< String> addFbPage(@RequestBody FaceBookPage p) {
        if (p==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        faceBookPageDao.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/ytchannel")
    @ResponseBody
    public ResponseEntity< String> addYtChannel(@RequestBody YouTubeChannel ytch) {
        if (ytch==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        youTubeChannelDao.save(ytch);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/fbpage")
    @ResponseBody
    public ResponseEntity< String> removeFbPage(@RequestBody FaceBookPage p){
        if (p == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        faceBookPageDao.delete(p.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/ytchannel")
    @ResponseBody
    public ResponseEntity< String> removeYtChannel(@RequestBody YouTubeChannel ytch){
        if (ytch == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        youTubeChannelDao.delete(ytch.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
