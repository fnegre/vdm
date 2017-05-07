package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

public class RssVdmParserTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmParserTest.class);


    RssVdmParser parser = new RssVdmParser();
    @Test
    public void readFeed() throws Exception {
        InputStream inputStream = readXmlFile("rss/vdm_rss_page_1.xml");
        List<Vdm> result = parser.readFeed(inputStream);
        LOGGER.info(result.get(0).toString());
        Assertions.assertThat(result).hasSize(20);
        Assertions.assertThat(result)
                .extracting("author", String.class)
                .contains("ToiTuVasÊtreViré", "Jarod", "Vanessa");
    }

    private InputStream readXmlFile(String classPathFile) {
        return this.getClass().getClassLoader().getResourceAsStream(classPathFile);
    }

}