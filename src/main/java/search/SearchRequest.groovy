package search

import groovy.json.JsonOutput
import org.apache.camel.Exchange
import org.apache.camel.PropertyInject

import javax.enterprise.context.ApplicationScoped
import java.nio.file.Files
import java.nio.file.StandardCopyOption

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING

@ApplicationScoped
class SearchRequest {

    @PropertyInject('search.jql')
    private String searchJql

    @PropertyInject('search.pageSize')
    private int searchPageSize

    void toSearchResultRangeRequest(Exchange exchange) {
        exchange.in.body = newSearchRequest()
    }

    void toSearchResultPageRequests(Exchange exchange) {
        def total = exchange.in.body.total as int

        exchange.in.body = (0..Math.ceil(total / searchPageSize) - 1).collect {
            newSearchRequest(it * searchPageSize as int, searchPageSize)
        }
    }

    static void setBodyToIssues(Exchange exchange) {
        exchange.in.body = exchange.in.body.issues
    }

    private String newSearchRequest(int page = 0, int pageSize = 1) {
        JsonOutput.toJson([
                jql       : searchJql,
                startAt   : page,
                maxResults: pageSize
        ])
    }

    static void saveIssue(Exchange exchange) {
        def data = exchange.in.body

        def jsonString = JsonOutput.toJson(data)
        def prettyJsonString = JsonOutput.prettyPrint(jsonString)

        def sourceFile = File.createTempFile('jira_', '.json')
        sourceFile.write(prettyJsonString)

        def destinationFileName = data.key
        def destinationFile = new File(destinationFileName + '.json')

        Files.move(sourceFile.toPath(), destinationFile.toPath(), REPLACE_EXISTING)
    }

}
