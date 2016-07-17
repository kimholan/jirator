package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
class CamelContextProducer {

    @Produces
    @ApplicationScoped
    CamelContext customize() {
        var context = new DefaultCamelContext();
        context.setName(CamelContextPropertiesInitializer.class.getSimpleName());
        return context;
    }

}
