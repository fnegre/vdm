package fr.fnegre.infrastructure.rss;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RssVdmDownloaderSteps {
    @Autowired
    private VdmDownloader downloader;
    private List<Vdm> result;

    @Given("^the downloader exists$")
    public void theDownloaderExists() throws Throwable {
        if (downloader == null) {
            throw new Exception("RssVdmDownloader instance should not be null");
        }
    }

    @When("^the client calls the downloader with (\\d+) as count argument$")
    public void theClientCallsTheDownloaderWithAsCountArgument(int count) throws Throwable {
        result = downloader.downloadVdm(count);
    }

    @Then("^it should answer with (\\d+) elements$")
    public void itShouldAnswerWithElements(int count) throws Throwable {
        Assertions.assertThat(result).hasSize(count);
    }

}
