package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssVdmDownloader implements VdmDownloader{

    private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmDownloader.class);

    @Autowired
    private RssVdmConfiguration configuration;

    private RssVdmParser parser = new RssVdmParser();

    @Override
    public List<Vdm> downloadVdm(int vdmNumberToDownload) {
        List<Vdm> vdms = new ArrayList<>();
        int rssPageNumber = 1;
        boolean continueDownload = true;
        while (vdms.size() < vdmNumberToDownload
                && continueDownload) {
            URL url = buildUrl(rssPageNumber);
            LOGGER.info("download url " + url);
            InputStream inputStream = read(url);
            List<Vdm> lastVdmsDownloaded = parser.parsePartialFeed(inputStream,vdmNumberToDownload - vdms.size());
            vdms.addAll(lastVdmsDownloaded);
            LOGGER.info("" + vdms.size() + "/" + vdmNumberToDownload + "vdms downloaded");
        }
        return vdms;
    }

    private URL buildUrl(int pageNumber) {
        URL url = null;
        try {
            url = new URL(configuration.getUrl() + pageNumber);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    private InputStream read(URL url) {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Test/0.1");
            return httpcon.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConfiguration(RssVdmConfiguration configuration) {
        this.configuration = configuration;
    }
}
