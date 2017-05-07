package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RssVdmDownloader implements VdmDownloader{

    @Autowired
    private RssVdmConfiguration configuration;

    @Override
    public List<Vdm> downloadVdm(int count) {
        return new ArrayList<>();
    }

    public void setConfiguration(RssVdmConfiguration configuration) {
        this.configuration = configuration;
    }
}
