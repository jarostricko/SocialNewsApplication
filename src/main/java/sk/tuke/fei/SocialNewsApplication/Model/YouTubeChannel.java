package sk.tuke.fei.SocialNewsApplication.Model;

import javax.persistence.*;

/**
 * Created by Jaro on 26.01.2018.
 */
@Entity
@Table(name = "YTCHANNEL")
public class YouTubeChannel {
    private Integer id;
    private String channelName;
    private String channelId;
    private String keywords;
    private boolean active;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    public Integer getId() { return id; }

    @Column(name = "CHANNELNAME")
    public String getChannelName() { return channelName; }

    @Column(name = "CHANNELID")
    public String getChannelId() { return channelId; }

    @Column(name = "ACTIVE")
    public boolean isActive() { return active; }

    @Column(name = "KEYWORDS")
    public String getKeywords() { return keywords; }

    public void setKeywords(String keywords) { this.keywords = keywords; }

    public void setId(Integer id) { this.id = id; }

    public void setChannelName(String channelName) { this.channelName = channelName; }

    public void setChannelId(String channelId) { this.channelId = channelId; }

    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "FaceBook Page[id = " + id + ", page name = " + channelName + "]";
    }

}
