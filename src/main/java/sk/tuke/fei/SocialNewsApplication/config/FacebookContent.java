package sk.tuke.fei.SocialNewsApplication.config;

import com.restfb.*;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;
import com.restfb.types.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.fei.SocialNewsApplication.Model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jaro on 09.10.2017.
 */
@Component
public class FacebookContent {
    private List<FaceBookPage> pages;
    private List<Credentials> credentials;

    private int limit = 20;
    private int daysLimit = 30;
    private boolean newestFirst = true;
    private boolean mostPopularFirst = false;

    private FacebookClient facebookClient;
    private List<Post> posts = new ArrayList<>();
    private List<Post> videos = new ArrayList<>();

    @Autowired
    private ConfigDao configDao;
    @Autowired
    private CredentialsDao credentialsDao;
    @Autowired
    private FaceBookPageDao faceBookPageDao;


    public void setCredentials() {
        credentials = (List<Credentials>) credentialsDao.findAll();
        Credentials cred;
        if (credentials.isEmpty()) throw new NullPointerException("Missing credentials in database.");
        cred = credentials.get(0);
        FacebookClient.AccessToken accessToken = new DefaultFacebookClient(Version.VERSION_2_9).obtainAppAccessToken(cred.getFbAppId(), cred.getFbAppSecret());
        facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_9);
    }

    private void updateConfigs() {
        if (credentials == null) setCredentials();
        List<Config> configList = (List<Config>) configDao.findAll();
        if (configList.isEmpty()) throw new NullPointerException("Missing configs in database.");
        Config config = configList.get(0);
        limit = config.getFbNumberOfPostsToFetch();
        daysLimit = config.getOldMaxDays();
        newestFirst = config.isNewestFirst();
        mostPopularFirst = config.isMostPopularFirst();
        pages = (List<FaceBookPage>) faceBookPageDao.findAll();
    }

    /**
     * Method fot sorting list of Facebook Post, based on count of number of reactions(likes, etc), comments and shares.
     *
     * @param list is list of Post to be sorted.
     */
    private void sortByPopularity(List<Post> list) {
        list.sort((p1, p2) -> {
            int thisCount = (int) (p1.getReactionsCount() + p1.getCommentsCount() + p1.getSharesCount());
            int tmpCount = (int) (p2.getReactionsCount() + p2.getCommentsCount() + p2.getSharesCount());
            if (thisCount < tmpCount) return 1;
            else if (thisCount > tmpCount) return -1;
            else return 0;
        });
    }

    private void sortByCreateDate(List<Post> list) {
        list.sort((p1, p2) -> {
            Date thisDate = p1.getCreatedTime();
            Date tmpDate = p2.getCreatedTime();
            if (thisDate.before(tmpDate)) return 1;
            else if (thisDate.after(tmpDate)) return -1;
            else return 0;
        });
    }

    private Connection<Post> fetchConnection(FaceBookPage p) {
        return facebookClient.fetchConnection(p.getPageId() + "/posts", Post.class,
                Parameter.with("limit", limit),
                Parameter.with("fields", "permalink_url,type,link,parent_id,created_time,name," +
                        "reactions.limit(0).summary(true)," +
                        "comments.limit(0).summary(true)," +
                        "shares.limit(0).summary(true)"));
    }

    /**
     * Method updates posts to be shown by App, stores them in static List<Post> posts.
     * Posts are sorted. Integer limit specifies maximal number of posts to be added to List.
     * List contains only facebook post and do not contains facebook videos.
     */
    private void updatePostsToShow() {
        updateConfigs();
        for (FaceBookPage p : pages) {
            if (p.isActive()) {
                System.out.println(p.toString());
                Connection<Post> result = fetchConnection(p);
                List<Post> resultList = new ArrayList<>();
                int limit = this.limit * 2;
                exportDataAsList(result, resultList, limit);
                sortByPopularity(resultList);
                limit = this.limit;
                for (Post post : resultList) {
                    if (!(post.getType().equals("video") && post.getLink().contains("facebook.com/"))) {
                        if (!posts.contains(post) && post.getCreatedTime().after(getDateBeforeLimit())) {
                            if (!post.getType().equals("event")) {
                                posts.add(post);
                                limit--;
                            }
                        }
                    }
                    if (limit <= 0) break;
                }
            }
        }

        if (mostPopularFirst) sortByPopularity(posts);
        else sortByCreateDate(posts);
    }

    private java.util.Date getDateBeforeLimit() {
        LocalDate minusDayLimit = LocalDate.now().minusDays(daysLimit);
        Date in = Date.from(minusDayLimit.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return in;
    }

    /**
     * Method updates posts to be shown by App, stores them in static List<Post> videos.
     * Posts are sorted. Integer limit specifies maximal number of videos to be added to List.
     * List contains only facebook videos and nothing more.
     */
    private void updateVideosToShow() {
        updateConfigs();
        for (FaceBookPage p : pages) {
            if (p.isActive()) {
                System.out.println(p.toString());
                Connection<Post> result = fetchConnection(p);
                List<Post> resultList = new ArrayList<>();
                int limit = this.limit * 2;
                exportDataAsList(result, resultList, limit);
                sortByPopularity(resultList);
                limit = this.limit;
                for (Post post : resultList) {
                    if (post.getType().equals("video") && post.getLink().contains("facebook.com/")) {
                        if (!videos.contains(post) && post.getCreatedTime().after(getDateBeforeLimit())) {
                            if (!post.getType().equals("event")) {
                                videos.add(post);
                                limit--;
                            }
                        }
                    }
                    if (limit <= 0) break;
                }
            }
        }
        if (mostPopularFirst) sortByPopularity(videos);
        else sortByCreateDate(videos);
    }

    private void exportDataAsList(Connection<Post> result, List<Post> resultList, int limit) {
        for (List<Post> page : result) {
            for (Post post : page) {
                resultList.add(post);
                limit--;
                if (limit <= 0) break;
            }
            if (limit <= 0) break;
        }
    }

    public NamedFacebookType getNextFacebookPost() {
        System.out.println("Posts To Show : " + posts.size());
        //if list of posts is empty, list is going to be updated
        if (posts.isEmpty()) updatePostsToShow();
        if (posts.isEmpty()) return null;
        Post nextPost = posts.get(0);
        System.out.println("Total count score: " + (nextPost.getReactionsCount() + nextPost.getSharesCount() + nextPost.getCommentsCount()));
        //post which is going to be showed is removed from list
        posts.remove(0);
        return nextPost;
    }

    public NamedFacebookType getNextFacebookVideo() {
        System.out.println("Videos To Show : " + videos.size());
        if (videos.isEmpty()) updateVideosToShow();
        if (videos.isEmpty()) return null;
        Post nextPost = videos.get(0);
        System.out.println("Total count score: " + (nextPost.getReactionsCount() + nextPost.getSharesCount() + nextPost.getCommentsCount()));
        //post which is going to be showed is removed from list
        videos.remove(0);
        Video video = null;
        if (nextPost.getLink().contains("facebook.com/") && nextPost.getLink().contains("video")) {
            System.out.println(nextPost.getLink());
            String[] ids;
            if (nextPost.getParentId() == null) ids = nextPost.getId().split("_");
            else ids = nextPost.getParentId().split("_");
            try {
                video = facebookClient.fetchObject(ids[1], Video.class, Parameter.with("fields", "length,embeddable,format,title"));
            } catch (FacebookOAuthException e) {
                e.printStackTrace();
            }
        } else return null;
        return video;
    }
}
