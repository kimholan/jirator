import org.jboss.weld.environment.se.StartMain;

public class Cli {

    public static void main(String[] cliArguments) throws InterruptedException {
        StartMain.main(cliArguments);

        Thread.currentThread().join();
    }

}
