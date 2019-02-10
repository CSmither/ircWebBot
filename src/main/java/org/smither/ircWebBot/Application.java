package org.smither.ircWebBot;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.rollbar.notifier.config.ConfigBuilder.withAccessToken;

@SpringBootApplication
public class Application {
	private static Rollbar rollbar;

	public static void main(String[] args) {
		Config config = withAccessToken("f42e52144a9d4159815aeb48bb6ba363").environment("production").codeVersion("1.0.0").build();
		rollbar = Rollbar.init(config);
		SpringApplication.run(Application.class, args);
	}
}
