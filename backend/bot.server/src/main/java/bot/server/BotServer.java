package bot.server;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackageClasses = {Launcher.class, ControllerPackageMarker.class})
public class BotServer {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(BotServer.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
