package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class RssVdmParserTest {

    //private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmParserTest.class);

    private RssVdmParser parser = new RssVdmParser();

    @Test
    public void parseAllFeed() throws Exception {
        InputStream inputStream = readXmlFile("rss/vdm_rss_page_1.xml");
        List<Vdm> result = parser.parseAllFeed(inputStream);
        Assertions.assertThat(result).hasSize(20);
        Assertions.assertThat(result)
                .extracting("author", String.class)
                .contains("ToiTuVasÊtreViré", "Jarod", "Vanessa");
    }

    @Test
    public void parsePartialFeed() throws Exception {
        InputStream inputStream = readXmlFile("rss/vdm_rss_page_1.xml");
        List<Vdm> result = parser.parsePartialFeed(inputStream, 5);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result)
                .extracting("author", String.class)
                .contains("ToiTuVasÊtreViré", "Jarod", "Vanessa");
    }

    @Test
    public void parsePartialFeedWithTooBigValue() throws Exception {
        InputStream inputStream = readXmlFile("rss/vdm_rss_page_1.xml");
        List<Vdm> result = parser.parsePartialFeed(inputStream, 150);
        Assertions.assertThat(result).hasSize(20);
        Assertions.assertThat(result)
                .extracting("author", String.class)
                .contains("ToiTuVasÊtreViré", "Jarod", "Vanessa");
    }


    private InputStream readXmlFile(String classPathFile) {
        return this.getClass().getClassLoader().getResourceAsStream(classPathFile);
    }

}