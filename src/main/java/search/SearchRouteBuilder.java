package search;

import jira.JiraConsumer;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@ApplicationScoped
class SearchRouteBuilder
        extends RouteBuilder {

    @Inject
    private SearchRequest searchRequest;

    @Inject
    private JiraConsumer jiraConsumer;

    @Inject
    private ExecutorService executorService;

    public void configure() {
        // Periodically excecute the search for a range of search result pages
        from("timer://jdkTimer?delay={{search.delay}}&period={{search.period}}")
                .to("direct:search:result-range");

        // Given a the total range of results, generate requests for all the result pages
        from("direct:search:result-range")
                .process(searchRequest::toSearchResultRangeRequest)
                .to("log:search:result-range:request?showAll=true&multiline=true&level={{search.log.level}}")
                .process(jiraConsumer::search)
                .to("log:search:result-range:response?showAll=true&multiline=true&level={{search.log.level}}")
                .convertBodyTo(Map.class)
                .process(searchRequest::toSearchResultPageRequests)
                .split().body().parallelProcessing().executorService(executorService)
                .to("direct:search:result-page")
        ;

        // Perform the search for a page of results
        from("direct:search:result-page")
                .to("log:search:result-page:request?showAll=true&multiline=true&level={{search.log.level}}")
                .process(jiraConsumer::search)
                .convertBodyTo(Map.class)
                .to("log:search:result-page:response?showAll=true&multiline=true&level={{search.log.level}}")
                .process(SearchRequest::setBodyToIssues)
                .split().body()
                .to("seda:search:issue?waitForTaskToComplete=Never");

        from("seda:search:issue")
                .process(SearchRequest::saveIssue)
                .to("log:search:issue?showAll=true&multiline=true&level={{search.log.level}}");

    }

}
