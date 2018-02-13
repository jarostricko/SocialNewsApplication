package sk.tuke.fei.SocialNewsApplication.Model;

import javax.persistence.*;

/**
 * Created by Jaro on 09.10.2017.
 */
@Entity
@Table(name = "FBPAGE")
public class FaceBookPage {
    private Integer id;
    private String pageName;
    private String pageId;
    private boolean active;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    @Column(name = "PAGENAME")
    public String getPageName() {
        return pageName;
    }

    @Column(name = "PAGEID")
    public String getPageId() {
        return pageId;
    }

    @Column(name = "ACTIVE")
    public boolean isActive() { return active; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "FaceBook Page[id = " + id + ", page name = " + pageName + "]";
    }


}
