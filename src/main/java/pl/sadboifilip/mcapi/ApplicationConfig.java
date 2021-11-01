package pl.sadboifilip.mcapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.rest.RESTApp;
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
            ApplicationConfig.restApp = new RESTApp();
        }
        return ApplicationConfig.restApp;
    }

    @Bean
    SseClientService getSseClientService() {
        return SseClientService.getInstance();
    }
}
