package fr.fnegre.infrastructure.rss;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import fr.fnegre.domain.model.downloading.Vdm;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;

import java.util.List;

public class RssVdmDownloaderSteps {
    private RssVdmDownloader downloader;
    private List<Vdm> result;

    @Given("^the downloader exists$")
    public void theDownloaderExists() throws Throwable {
        downloader = new RssVdmDownloader();
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
