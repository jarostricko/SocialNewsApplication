package sk.tuke.fei.SocialNewsApplication.config;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.fei.SocialNewsApplication.Model.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaro on 27.12.2017.
 */
@Component
public class YoutubeContent {
    private List<YouTubeChannel> channels;

    private boolean youtubeBasedOnChannelsOn;
    private boolean youtubeBasedOnQueryTermsOn;

    private String apiKey;
    private String order;
    private int maxResults;
    private List<SearchResult> videos = new ArrayList<>();

    @Autowired
    private ConfigDao configDao;
    @Autowired
    private CredentialsDao credentialsDao;
    @Autowired
    private YouTubeChannelDao youTubeChannelDao;

    /**
     * Build and return an authorized API client service, such as a YouTube
     * Data API client service.
     *
     * @return an authorized API client service
     */
    public YouTube getYouTubeService() {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException { }
        }).setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey))
                .setApplicationName("social-news-application-tuke-sk").build();
        return youtube;
    }

    private void updateConfigs() {
        List<Credentials> credentials = (List<Credentials>) credentialsDao.findAll();
        Credentials cred;
        if (credentials.isEmpty()) throw new NullPointerException("Missing credentials in database.");
        cred = credentials.get(0);
        apiKey = cred.getYouTubeApiKey();
        List<Config> configList = (List<Config>) configDao.findAll();
        if (configList.isEmpty()) throw new NullPointerException("Missing configs in database.");
        Config config = configList.get(0);
        maxResults = config.getYtNumberOfPostsToFetch();
        youtubeBasedOnChannelsOn = config.isYoutubeBasedOnChannelsOn();
        youtubeBasedOnQueryTermsOn = config.isYoutubeBasedOnQueryTermsOn();
        order = config.getVideoOrder();
        channels = (List<YouTubeChannel>) youTubeChannelDao.findAll();
    }

    public void updateVideosToShow() {
        updateConfigs();
        YouTube youtube = getYouTubeService();
        List<SearchResult> searchResultList = null;
        String channelId;
        String query;

        for (YouTubeChannel ytch : channels) {
            channelId = ytch.getChannelId();
            query = ytch.getKeywords();
            if (ytch.isActive()) {
                try {
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("part", "id");
                    parameters.put("maxResults", Integer.toString(maxResults));
                    parameters.put("type", "video");
                    parameters.put("videoEmbeddable", "true");
                    parameters.put("order", order);

                    if (youtubeBasedOnQueryTermsOn && youtubeBasedOnChannelsOn){
                        parameters.put("q", query);
                        parameters.put("channelId", channelId);
                    }else {
                        if (youtubeBasedOnChannelsOn){
                            parameters.put("q", "");
                            parameters.put("channelId", channelId);
                        }else if (youtubeBasedOnQueryTermsOn){
                            parameters.put("q", query);
                            parameters.put("channelId", "");
                        }else {
                            return;
                        }
                    }
                    YouTube.Search.List searchListByKeywordRequest = youtube.search().list(parameters.get("part"));
                    if (parameters.containsKey("maxResults")) {
                        searchListByKeywordRequest.setMaxResults(Long.parseLong(parameters.get("maxResults")));
                    }
                    if (parameters.containsKey("q") && parameters.get("q") != "") {
                        searchListByKeywordRequest.setQ(parameters.get("q"));
                    }
                    if (parameters.containsKey("type") && parameters.get("type") != "") {
                        searchListByKeywordRequest.setType(parameters.get("type"));
                    }
                    if (parameters.containsKey("videoEmbeddable") && parameters.get("videoEmbeddable") != "") {
                        searchListByKeywordRequest.setVideoEmbeddable(parameters.get("videoEmbeddable"));
                    }
                    if (parameters.containsKey("channelId") && parameters.get("channelId") != "") {
                        searchListByKeywordRequest.setChannelId(parameters.get("channelId"));
                    }
                    if (parameters.containsKey("order") && parameters.get("order") != "") {
                        searchListByKeywordRequest.setOrder(parameters.get("order"));
                    }
                    SearchListResponse response = searchListByKeywordRequest.execute();
                    searchResultList = response.getItems();
                } catch (GoogleJsonResponseException e) {
                    e.printStackTrace();
                    System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                if (searchResultList != null) {
                    videos.addAll(searchResultList);
                }
            }
        }
    }

    public NextPost getNextYoutubeVideo() {
        System.out.println("Videos To Show : " + videos.size());
        if (videos.isEmpty()) updateVideosToShow();
        if (videos.isEmpty()) return null;
        SearchResult video = videos.get(0);
        String duration = null;
        String html = null;
        String title = null;
        BigInteger viewCount;
        BigInteger likeCount;
        BigInteger dislikeCount;
        BigInteger commentCount;
        BigInteger totalCount = null;

        YouTube.Videos.List videosListByIdRequest;
        try {
            videosListByIdRequest = getYouTubeService().videos().list("snippet,contentDetails,player,statistics");
            VideoListResponse response = videosListByIdRequest.setId(video.getId().getVideoId()).execute();
            List<Video> items = response.getItems();
            duration = items.get(0).getContentDetails().getDuration();
            title = items.get(0).getSnippet().getTitle();
            html = items.get(0).getPlayer().getEmbedHtml();
            viewCount = items.get(0).getStatistics().getViewCount();
            likeCount = items.get(0).getStatistics().getLikeCount();
            dislikeCount = items.get(0).getStatistics().getDislikeCount();
            commentCount = items.get(0).getStatistics().getCommentCount();
            totalCount = (viewCount.add(likeCount.add(commentCount))).subtract(dislikeCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Period p = ISOPeriodFormat.standard().parsePeriod(duration);

        NextPost nextPost = new NextPost();
        nextPost.setVideo(true);
        nextPost.setTimeLimit(p.toStandardSeconds().getSeconds() * 1000);
        nextPost.setPostId(video.getId().getVideoId());
        nextPost.setPostHeight(0);
        nextPost.setPostWidth(0);
        nextPost.setPostType(video.getId().getKind());
        nextPost.setIframeHtml(html);
        nextPost.setPostName(title);
        System.out.println("Total count score: " + totalCount);
        //post which is going to be showed is removed from list
        videos.remove(0);
        return nextPost;
    }
}
