package jira

import groovy.json.JsonSlurper
import org.apache.camel.Exchange
import org.apache.camel.ProducerTemplate
import org.apache.camel.cdi.Uri

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
class JiraConsumer {

    @Inject
    @Uri('{{jira.url}}/search?authUsername={{jira.user}}&authPassword={{jira.pass}}&authenticationPreemptive=true')
    private ProducerTemplate searchEndpoint

    @Inject
    @Named('jiraRequestHeaders')
    private Map<String, Object> jiraRequestHeaders

    void search(Exchange exchange) {
        def requestJson = exchange.in.getBody(String)
        def stream = searchEndpoint.requestBodyAndHeaders(requestJson, jiraRequestHeaders) as InputStream
        def map = new JsonSlurper().parse(stream, 'utf-8')

        exchange.out.body = map
    }

}
