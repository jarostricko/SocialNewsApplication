package sk.tuke.fei.SocialNewsApplication.Model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Jaro on 26.01.2018.
 */
@Transactional
public interface VotesDao extends CrudRepository<Votes,Integer> {
    List<Votes> findAllByPostType(String postType);
}
