package bot.launcher;

import bot.launcher.controller.ControllerPackageMarker;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackageClasses = {Launcher.class, ControllerPackageMarker.class})
public class Launcher {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(Launcher.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
