package sk.tuke.fei.SocialNewsApplication.Model;

import javax.persistence.*;

/**
 * Created by Jaro on 09.10.2017.
 */
@Entity
@Table(name = "CREDENTIALS")
public class Credentials {
    private int id;
    private String fbAppId;
    private String fbAppSecret;
    private String youTubeApiKey;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    @Column(name = "fbappid")
    public String getFbAppId() {
        return fbAppId;
    }

    @Column(name = "fbappsecret")
    public String getFbAppSecret() {
        return fbAppSecret;
    }

    @Column(name = "youtubeapikey")
    public String getYouTubeApiKey() {
        return youTubeApiKey;
    }

    public void setFbAppId(String fbAppId) {
        this.fbAppId = fbAppId;
    }

    public void setFbAppSecret(String fbAppSecret) {
        this.fbAppSecret = fbAppSecret;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYouTubeApiKey(String youTubeApiKey) { this.youTubeApiKey = youTubeApiKey; }
}
