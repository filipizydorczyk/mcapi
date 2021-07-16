package pl.sadboifilip.mcapi.rest.endpoints;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.javalin.apibuilder.EndpointGroup;
import pl.sadboifilip.mcapi.App;
import pl.sadboifilip.mcapi.ApplicationConfig;

public abstract class BaseEndpointGroup implements EndpointGroup {

    protected App app;

    public BaseEndpointGroup() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);

        try {
            this.app = context.getBean(App.class);
        } finally {
            context.close();
        }
    }

    @Override
    public void addEndpoints() {
    }

}
