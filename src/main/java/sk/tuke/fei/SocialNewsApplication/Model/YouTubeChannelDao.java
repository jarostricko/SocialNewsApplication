package sk.tuke.fei.SocialNewsApplication.Model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Jaro on 26.01.2018.
 */
@Transactional
public interface YouTubeChannelDao extends CrudRepository<YouTubeChannel,Integer> {
}
