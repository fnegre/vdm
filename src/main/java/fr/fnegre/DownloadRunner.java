package fr.fnegre;

import fr.fnegre.domain.service.VdmDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DownloadRunner implements CommandLineRunner {

    @Autowired
    private VdmDownloader downloader;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("download " + downloader.downloadVdm(10));
    }
}
