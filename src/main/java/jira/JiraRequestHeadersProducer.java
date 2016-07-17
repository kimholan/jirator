package jira;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.util.Map;

import static org.apache.http.HttpHeaders.ACCEPT_ENCODING;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

public class JiraRequestHeadersProducer {

    @Produces
    @Named("jiraRequestHeaders")
    @ApplicationScoped
    Map<String, Object> produceRequestHeaders() {
        return Map.of(
                CONTENT_TYPE, "application/json",
                ACCEPT_ENCODING, "gzip,deflate",
                "X-Atlassian-Token", "no-check"
        );
    }

}
