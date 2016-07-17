package camel;

import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.management.event.CamelContextStartingEvent;

import javax.enterprise.event.Observes;

public class CamelHttps4ComponentConfigurer {

    void configureHttps4Component(@Observes CamelContextStartingEvent event) {
        var context = event.getContext();

        // https4 configuration
        var https = context.getComponent("https4", HttpComponent.class);
        https.setConnectionsPerRoute(64);
        https.setConnectionsPerRoute(32);
    }

}
