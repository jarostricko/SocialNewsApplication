package sk.tuke.fei.SocialNewsApplication.config;

import com.restfb.types.Post;
import com.restfb.types.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.fei.SocialNewsApplication.Model.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Jaro on 09.10.2017.
 */
@Component
public class ContentManager {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private NextPost currentPost;
    private static int i = 10;
    @Autowired
    private FacebookContent facebookContent;
    @Autowired
    private YoutubeContent youtubeContent;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private VotesDao votesDao;
    private boolean set = false;
    private int postTime = 5;
    private boolean facebookPostsOn, facebookVideosOn, youtubeBasedOnChannelsOn, youtubeBasedOnQueryTermsOn;


    public NextPost getNextPost() {
        if (!set) setConfigs();

        if (i > 0) {
            if (i == 5) {
                i--;
                if (facebookVideosOn) {
                    currentPost = convert((Video) facebookContent.getNextFacebookVideo());
                    return currentPost;
                }
                else {
                    if (youtubeBasedOnQueryTermsOn || youtubeBasedOnChannelsOn) {
                        currentPost = youtubeContent.getNextYoutubeVideo();
                        return currentPost;
                    }
                    else{
                        currentPost = convert((Post) facebookContent.getNextFacebookPost());
                        return currentPost;
                    }
                }
            } else {
                i--;
                if (facebookPostsOn){
                    currentPost = convert((Post) facebookContent.getNextFacebookPost());
                    return currentPost;
                }
                else {
                    if ((youtubeBasedOnQueryTermsOn || youtubeBasedOnChannelsOn) && i%2!=0){
                        currentPost = youtubeContent.getNextYoutubeVideo();
                        return currentPost;
                    }
                    if (facebookVideosOn){
                        currentPost = convert((Video) facebookContent.getNextFacebookVideo());
                        return currentPost;
                    }
                    else return null;
                }
            }
        } else {
            i = 10;
            if (youtubeBasedOnQueryTermsOn || youtubeBasedOnChannelsOn){
                currentPost = youtubeContent.getNextYoutubeVideo();
                return currentPost;
            }
            else {
                if (facebookVideosOn){
                    currentPost = youtubeContent.getNextYoutubeVideo();
                    return currentPost;
                }
                else{
                    currentPost = convert((Post) facebookContent.getNextFacebookPost());
                    return currentPost;
                }
            }
        }
    }

    public void setConfigs() {
        List<Config> configList = (List<Config>) configDao.findAll();
        if (configList.isEmpty()) throw new NullPointerException("Missing configs in database.");
        Config config = configList.get(0);
        facebookPostsOn = config.isFacebookPostsOn();
        facebookVideosOn = config.isFacebookVideosOn();
        postTime = config.getPostTime();
        youtubeBasedOnChannelsOn = config.isYoutubeBasedOnChannelsOn();
        youtubeBasedOnQueryTermsOn = config.isYoutubeBasedOnQueryTermsOn();
        if (!facebookPostsOn && !facebookVideosOn && !youtubeBasedOnQueryTermsOn && !youtubeBasedOnChannelsOn) facebookPostsOn = true;
        set = true;
    }

    private NextPost convert(Video video) {
        if (video==null)return null;
        NextPost nextPost = new NextPost();
        int height = 700;
        int width = 1000;
        for (Video.VideoFormat format : video.getFormat()) {
            if (format.getFilter().equals("native")) {
                height = format.getHeight();
                width = format.getWidth();
            }
        }
        System.out.println("h: " + height + " and  w: " + width);
        nextPost.setPostHeight(height);
        nextPost.setPostWidth(width);
        nextPost.setPostId(video.getId());
        nextPost.setPostType("fb#video");
        nextPost.setTimeLimit((int) (video.getLength() * 1000));
        nextPost.setVideo(true);
        nextPost.setPostName(video.getTitle());
        return nextPost;
    }

    private NextPost convert(Post post) {
        if (post==null)return null;
        NextPost nextPost = new NextPost();
        nextPost.setVideo(false);
        nextPost.setPostId(post.getId());
        nextPost.setPostUrl(post.getPermalinkUrl());
        //nextPost.setPostType(post.getType());
        nextPost.setPostType("fb#post");
        nextPost.setTimeLimit(postTime * 1000);
        nextPost.setPostName(post.getName());
        return nextPost;
    }

    public void voteCurrentPost(boolean like){
        if (currentPost == null) return;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Votes vote = new Votes();
        vote.setDate(timestamp);
        vote.setDisliked(!like);
        vote.setLiked(like);
        vote.setPost_id(currentPost.getPostId());
        vote.setPostType(currentPost.getPostType());
        votesDao.save(vote);
    }

    public NextPost getCurrentPost(){
        return currentPost;
    }

}
