package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2010UZ on 06/05/2017.
 */
public class RssVdmDownloader implements VdmDownloader{

    @Override
    public List<Vdm> downloadVdm(int count) {
        return new ArrayList<>();
    }
}
