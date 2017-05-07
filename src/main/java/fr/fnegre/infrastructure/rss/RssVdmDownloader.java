package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssVdmDownloader implements VdmDownloader{

    @Autowired
    private RssVdmConfiguration configuration;

    private RssVdmParser parser = new RssVdmParser();

    @Override
    public List<Vdm> downloadVdm(int count) {
        URL url = null;
        try {
            url = new URL(configuration.getBaseUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = read(url);
        parser.readFeed(inputStream);
        return new ArrayList<>();
    }

    private InputStream read(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setConfiguration(RssVdmConfiguration configuration) {
        this.configuration = configuration;
    }
}
