package sk.tuke.fei.SocialNewsApplication.Model;

public class VotesResult {
    private String postType;
    private int liked,disliked;
    private String postId;


    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getDisliked() {
        return disliked;
    }

    public void setDisliked(int disliked) {
        this.disliked = disliked;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void addLike() {
        this.liked++;
    }

    public void addDislike() {
        this.disliked++;
    }
}
