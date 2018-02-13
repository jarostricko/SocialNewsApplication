package sk.tuke.fei.SocialNewsApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "sk.tuke.fei")
public class SocialNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNewsApplication.class, args);
	}
}
