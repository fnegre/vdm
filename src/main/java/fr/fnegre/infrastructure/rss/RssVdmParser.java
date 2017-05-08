package fr.fnegre.infrastructure.rss;

import fr.fnegre.domain.model.downloading.Vdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RssVdmParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssVdmParser.class);
    private static final String RSS_ENTRY = "entry";
    private static final String PUB_DATE = "published";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    public List<Vdm> parseAllFeed(InputStream in) {
        return parsePartialFeed(in, -1);
    }
    public List<Vdm> parsePartialFeed(InputStream in, int numberOfVdmToParse) {
        List<Vdm> vdms = new ArrayList<>();
        int numberOfVdmAlreadyParsed = 0;
        try {
            // Set header values intial to the empty string
            String content = "";
            String author = "";
            String pubdate = "";
            LocalDateTime publishedDate = null;

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()
                    && (numberOfVdmAlreadyParsed < numberOfVdmToParse
                    || numberOfVdmToParse == -1)) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case RSS_ENTRY:
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
			if(author != null && content != null && publishedDate != null) {
	                        Vdm vdmEntry = new Vdm();
        	                vdmEntry.setAuthor(author);
                	        vdmEntry.setContent(content);
                        	vdmEntry.setPublishingDate(publishedDate);
                       	 	vdms.add(vdmEntry);
                        	numberOfVdmAlreadyParsed++;
				LOGGER.debug ("Parse VDM " + vdmEntry); 
			}
                        event = eventReader.nextEvent();
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
            result = event.asCharacters().getData();
	   if (result.equals("null")) {	
		return null;	
	  }
        }
        return result;
    }

    /**
     * Parse String of this pattern 2017-05-05T14:00:02+02:00
     * @param dateString
     * @return LocalDate
     */
    private static LocalDateTime parseRssDate(String dateString) {
	if (dateString == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(dateString, formatter);
    }
}