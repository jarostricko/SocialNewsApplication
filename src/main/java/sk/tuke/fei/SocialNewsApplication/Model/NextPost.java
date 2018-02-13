package sk.tuke.fei.SocialNewsApplication.Model;

/**
 * Created by Jaro on 09.10.2017.
 */
public class NextPost {
    private String postUrl;
    private String postId;
    private String postType;
    private int timeLimit;
    private int postHeight;
    private int postWidth;
    private boolean isVideo;
    private String iframeHtml;
    private String postName;

    public String getPostName() { return postName;}

    public void setPostName(String postName) { this.postName = postName;}



    public String getIframeHtml() { return iframeHtml; }

    public void setIframeHtml(String iframeHtml) { this.iframeHtml = iframeHtml; }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getPostHeight() {
        return postHeight;
    }

    public void setPostHeight(int postHeight) {
        this.postHeight = postHeight;
    }

    public int getPostWidth() {
        return postWidth;
    }

    public void setPostWidth(int postWidth) {
        this.postWidth = postWidth;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
