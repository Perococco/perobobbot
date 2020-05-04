package bot.launcher;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(Launcher.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
