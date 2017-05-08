package fr.fnegre.interfaces.rest.reading;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import fr.fnegre.VdmApplication;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {VdmApplication.class})
public class ReadingControllerSteps implements En {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadingControllerSteps.class);

    @Autowired
    private TestRestTemplate restTemplate;

    public ReadingControllerSteps() {
        AtomicReference<ResponseEntity<PostsView>> resultReferenceToPosts = new AtomicReference<>();
        AtomicReference<ResponseEntity<VdmView>> resultReferenceToOneVdm = new AtomicReference<>();
        AtomicInteger codeHttp = new AtomicInteger();
        Given("^the Rest API exists$", () -> {
            if (restTemplate == null) {
                throw new PendingException("restTemplate should not be null");
            }
        });
        When("^the client calls /api/posts$", () -> {
            ResponseEntity<PostsView> exchange = callUrl("/api/posts", PostsView.class);
            LOGGER.info("code http = " +exchange.getStatusCodeValue());
            resultReferenceToPosts.set(exchange);
            codeHttp.set(exchange.getStatusCodeValue());
        });
        Then("^it should answer with status code (\\d+)$", (Integer statusCode) -> {
            Assertions.assertThat(codeHttp.get()).isEqualTo(statusCode);
        });
        And("^it should answer with several VDM$", () -> {
            Assertions.assertThat(resultReferenceToPosts.get().getBody().getCount()).isGreaterThan(0);
        });
        When("^the client calls /api/posts/(\\d+)$", (Integer id) -> {
            ResponseEntity<VdmView> exchange = callUrl("/api/posts/" + id, VdmView.class);
            resultReferenceToOneVdm.set(exchange);
            codeHttp.set(exchange.getStatusCodeValue());
        });
        And("^it should answer with (\\d+) VDM$", (Integer numberOfVdmExpected) -> {
            Assertions.assertThat(resultReferenceToOneVdm.get().getBody()).isNotNull();
        });
        And("^the ID of the VDM should be (\\d+)$", (Integer id) -> {
            Assertions.assertThat(resultReferenceToOneVdm.get().getBody()).hasFieldOrPropertyWithValue("id", id);
        });
        And("^the content should not be empty$", () -> {
            Assertions.assertThat(resultReferenceToOneVdm.get().getBody().getContent()).isNotEmpty();
        });
    }

    private <E> ResponseEntity<E> callUrl(String url, Class<E> returnType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, returnType);
    }
}
