package executor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ExecutorServiceProducer {

    @Produces
    @ApplicationScoped
    ExecutorService executorService() {
        return Executors.newWorkStealingPool();
    }

}
