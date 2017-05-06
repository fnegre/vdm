package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RssVdmDownloaderTest {
    RssVdmConfiguration configuration = new RssVdmConfiguration();
    RssVdmDownloader downloader = new RssVdmDownloader();

    @Test
    public void downloadVdmOfOnePage() throws Exception {
        List<Vdm> result = downloader.downloadVdm(10);
        Assertions.assertThat(result).hasSize(10);
    }

    @Test
    public void downloadVdmOfTwoPages() throws Exception {
        List<Vdm> result = downloader.downloadVdm(30);
        Assertions.assertThat(result).hasSize(30);
    }
}