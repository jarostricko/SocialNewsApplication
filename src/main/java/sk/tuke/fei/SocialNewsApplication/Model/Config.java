package sk.tuke.fei.SocialNewsApplication.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Jaro on 09.10.2017.
 */
@Entity
@Table(name = "config")
public class Config {

    private int id;
    private int fbNumberOfPostsToFetch;
    private int ytNumberOfPostsToFetch;
    private int oldMaxDays;
    private boolean newestFirst;
    private boolean mostPopularFirst;
    private boolean facebookPostsOn;
    private boolean facebookVideosOn;
    private boolean youtubeBasedOnChannelsOn;
    private boolean youtubeBasedOnQueryTermsOn;
    private String videoOrder;
    private int postTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    @Column(name = "fb_number_of_posts_to_fetch")
    public int getFbNumberOfPostsToFetch() {
        return fbNumberOfPostsToFetch;
    }

    @Column(name = "yt_number_of_posts_to_fetch")
    public int getYtNumberOfPostsToFetch() {
        return ytNumberOfPostsToFetch;
    }

    @Column(name = "old_max_days")
    public int getOldMaxDays() {
        return oldMaxDays;
    }

    @Column(name = "newest_first")
    public boolean isNewestFirst() {
        return newestFirst;
    }

    @Column(name = "most_popular_first")
    public boolean isMostPopularFirst() {
        return mostPopularFirst;
    }

    @Column(name = "facebook_posts_on")
    public boolean isFacebookPostsOn() {
        return facebookPostsOn;
    }

    @Column(name = "facebook_videos_on")
    public boolean isFacebookVideosOn() {
        return facebookVideosOn;
    }

    @Column(name = "youtube_based_on_channels_on")
    public boolean isYoutubeBasedOnChannelsOn() {
        return youtubeBasedOnChannelsOn;
    }

    @Column(name = "youtube_based_on_query_terms_on")
    public boolean isYoutubeBasedOnQueryTermsOn() {
        return youtubeBasedOnQueryTermsOn;
    }

    @Column(name = "video_order")
    public String getVideoOrder() {
        return videoOrder;
    }

    @Column(name = "post_time")
    public int getPostTime() { return postTime;}

    public void setYtNumberOfPostsToFetch(int ytNumberOfPostsToFetch) {
        this.ytNumberOfPostsToFetch = ytNumberOfPostsToFetch;
    }

    public void setFacebookPostsOn(boolean facebookPostsOn) {
        this.facebookPostsOn = facebookPostsOn;
    }

    public void setFacebookVideosOn(boolean facebookVideosOn) {
        this.facebookVideosOn = facebookVideosOn;
    }

    public void setYoutubeBasedOnChannelsOn(boolean youtubeBasedOnChannelsOn) {
        this.youtubeBasedOnChannelsOn = youtubeBasedOnChannelsOn;
    }

    public void setYoutubeBasedOnQueryTermsOn(boolean youtubeBasedOnQueryTermsOn) {
        this.youtubeBasedOnQueryTermsOn = youtubeBasedOnQueryTermsOn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFbNumberOfPostsToFetch(int fbNumberOfPostsToFetch) {
        this.fbNumberOfPostsToFetch = fbNumberOfPostsToFetch;
    }

    public void setOldMaxDays(int oldMaxDays) {
        this.oldMaxDays = oldMaxDays;
    }

    public void setNewestFirst(boolean newestFirst) {
        this.newestFirst = newestFirst;
    }

    public void setMostPopularFirst(boolean mostPopularFirst) {
        this.mostPopularFirst = mostPopularFirst;
    }

    public void setVideoOrder(String videoOrder) {
        this.videoOrder = videoOrder;
    }

    public void setPostTime(int postTime) {
        this.postTime = postTime;
    }


}
