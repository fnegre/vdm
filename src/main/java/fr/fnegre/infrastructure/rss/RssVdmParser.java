package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RssVdmParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmParser.class);

    private static final String RSS_ENTRY = "entry";
    private static final String PUB_DATE = "published";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";


    public List<Vdm> readFeed(InputStream in) {
        List<Vdm> vdms = new ArrayList<>();
        try {
            boolean isFeedHeader = true;
            // Set header values intial to the empty string
            String content = "";
            String author = "";
            String pubdate = "";
            LocalDateTime publishedDate = null;

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            //InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case RSS_ENTRY:
                            if (isFeedHeader) {
                                isFeedHeader = false;
                            }
                            event = eventReader.nextEvent();
                            break;
                        case CONTENT:
                            content = getCharacterData(eventReader);
                            break;
                        case AUTHOR:
                            author = getCharacterDataOfChildTag(eventReader);
                            break;
                        case PUB_DATE:
                            pubdate = getCharacterData(eventReader);
                            publishedDate = RssVdmParser.parseRssDate(pubdate);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (RSS_ENTRY)) {
                        Vdm vdmEntry = new Vdm();
                        vdmEntry.setAuthor(author);
                        vdmEntry.setContent(content);
                        vdmEntry.setPublishingDate(publishedDate);
                        vdms.add(vdmEntry);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return vdms;
    }


    private String getCharacterDataOfChildTag(XMLEventReader eventReader)
            throws XMLStreamException {
        eventReader.nextTag();
        return getCharacterData(eventReader);
    }
    private String getCharacterData(XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            LOGGER.info("getCharacterData : char " + result);
            result = event.asCharacters().getData();
        }
        return result;
    }


    /**
     * Parse String of this pattern 2017-05-05T14:00:02+02:00
     * @param dateString
     * @return LocalDate
     */
    private static LocalDateTime parseRssDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(dateString, formatter);
    }
}