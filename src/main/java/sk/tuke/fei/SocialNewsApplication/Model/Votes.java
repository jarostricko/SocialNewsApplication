package sk.tuke.fei.SocialNewsApplication.Model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Jaro on 26.01.2018.
 */
@Entity
@Table(name = "VOTES")
public class Votes {
    private int id;
    private String postType;
    private String post_id;
    private Timestamp date;
    private boolean liked;
    private boolean disliked;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "post_type")
    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }
    @Column(name = "post_id")
    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    @Column(name = "liked")
    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    @Column(name = "disliked")
    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }
}
