package camel;

import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.management.event.CamelContextStartingEvent;

import javax.enterprise.event.Observes;

public class CamelHttp4ComponentConfigurer {

    void configureHttp4Component(@Observes CamelContextStartingEvent event) {
        var context = event.getContext();

        // http configuration
        var http = context.getComponent("http4", HttpComponent.class);
        http.setMaxTotalConnections(64);
        http.setConnectionsPerRoute(32);
    }

}
