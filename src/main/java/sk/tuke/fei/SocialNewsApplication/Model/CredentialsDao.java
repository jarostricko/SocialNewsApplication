package sk.tuke.fei.SocialNewsApplication.Model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Jaro on 09.10.2017.
 */
@Transactional
public interface CredentialsDao extends CrudRepository<Credentials, Integer>{
}
