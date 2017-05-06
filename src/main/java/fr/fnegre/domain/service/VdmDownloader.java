package fr.fnegre.domain.service;

import fr.fnegre.domain.model.downloading.Vdm;

import java.util.List;

public interface VdmDownloader {
    /**
     * Download a fixed number of VDM
     * @param count is the number of VDM to download
     * @return the VDM downloaded or empty list
     */
    List<Vdm> downloadVdm(int count);
}
