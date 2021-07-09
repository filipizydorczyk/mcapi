package pl.sadboifilip.mcapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.javalin.Javalin;
import pl.sadboifilip.mcapi.rest.RESTApp;

@Configuration
@ComponentScan("pl.sadboifilip.mcapi")
public class ApplicationConfig {

    @Bean
    public App pluginInstance() {
        return App.getPlugin(App.class);
    }

    @Bean
    public Javalin getJavalinApp() {
        return RESTApp.getApp();
    }
}
