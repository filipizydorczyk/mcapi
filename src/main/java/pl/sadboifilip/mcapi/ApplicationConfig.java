package pl.sadboifilip.mcapi;

import io.javalin.Javalin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.sadboifilip.mcapi.rest.RestServiceApplication;
import pl.sadboifilip.mcapi.rest.SseClientService;

@Configuration
@ComponentScan("pl.sadboifilip.mcapi")
public class ApplicationConfig {

    private static Javalin restApp;

    @Bean
    public App pluginInstance() {
        return App.getPlugin(App.class);
    }

    @Bean
    public Javalin getJavalinApp() {
        if (ApplicationConfig.restApp == null) {
            ApplicationConfig.restApp = new RestServiceApplication();
        }
        return ApplicationConfig.restApp;
    }

    @Bean
    SseClientService getSseClientService() {
        return SseClientService.getInstance();
    }
}
