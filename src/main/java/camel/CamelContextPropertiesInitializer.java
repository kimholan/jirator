package camel;

import org.apache.camel.component.properties.PropertiesComponent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Dependent
class CamelContextPropertiesInitializer {

    @Produces
    @Named("properties")
    @ApplicationScoped
    PropertiesComponent printHello() {
        return new PropertiesComponent("classpath:/jirator.properties");
    }

}
