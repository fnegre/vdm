package fr.fnegre;

import fr.fnegre.domain.model.downloading.Vdm;
import fr.fnegre.domain.service.VdmDownloader;
import fr.fnegre.infrastructure.persistence.VdmEntity;
import fr.fnegre.infrastructure.persistence.VdmRepository;
import fr.fnegre.infrastructure.rss.RssVdmDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DownloadRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmDownloader.class);

    @Autowired
    private VdmDownloader downloader;

    @Autowired
    private VdmRepository repository;

    @Override
    public void run(String... strings) throws Exception {
        //List<Vdm> vdms = downloader.downloadVdm(10);
        List<VdmEntity> vdmEntities =
                IntStream.range(1, 30).boxed().map(i -> {
            VdmEntity entity = new VdmEntity();
            entity.setAuthor("Flo");
            entity.setContent("Test");
            entity.setPublishingDate(Timestamp.valueOf(LocalDateTime.now()));
            return entity;
        }).collect(Collectors.toList());

/*        vdms.add(new Vdm());
                LOGGER.info("download " + vdms);
        List<VdmEntity> vdmEntities = vdms.stream().map(vdm -> {
            VdmEntity entity = new VdmEntity();
            entity.setAuthor(vdm.getAuthor());
            entity.setContent(vdm.getContent());
            entity.setPublishingDate(Timestamp.valueOf(vdm.getPublishingDate()));
            return entity;
        }).collect(Collectors.toList());*/
        LOGGER.info("Persistence des vdm");
        repository.save(vdmEntities);
        Iterable<VdmEntity> vdmsPersisted = repository.findAll();
        LOGGER.info("from database : " + vdmsPersisted.toString());
    }
}
