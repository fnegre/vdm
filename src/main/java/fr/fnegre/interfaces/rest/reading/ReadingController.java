package fr.fnegre.interfaces.rest.reading;

import fr.fnegre.infrastructure.persistence.VdmEntity;
import fr.fnegre.infrastructure.persistence.VdmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ReadingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadingController.class);

    @Autowired
    private VdmRepository repository;

    @RequestMapping(value = "/api/posts", method = RequestMethod.GET)
    public PostsView posts(@RequestParam(value="from", required = false) String fromStr,
                           @RequestParam(value="to", required = false) String toStr,
                           @RequestParam(value="author", required = false) String author) throws ParseException {
        LOGGER.info("Call to api/posts received");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date from = (fromStr != null) ? formatter.parse(fromStr) : null;
        Date to = (toStr != null) ? formatter.parse(toStr) : null;

        Iterable<VdmEntity> entities = new ArrayList<>();
        if (from == null && to == null && author == null) {
            entities = repository.findAll();
        } else if (from == null && to == null && author != null){
            entities = repository.findByAuthor(author);
        } else if (from == null && to != null && author == null) {
            entities = repository.findByPublishingDateBetween(new Date(), to);
        } else if (from != null && to == null && author == null) {
            entities = repository.findByPublishingDateBetween(from, new Date());
        } else if (from != null && to != null && author == null) {
            entities = repository.findByPublishingDateBetween(from, to);
        } else {
            entities = repository.findByAuthorAndPublishingDateBetween(author,
                    (from == null) ? new Date() : from,
                    (to == null) ? new Date() : to);
        }
        PostsView result = new PostsView();
        result.setPosts(StreamSupport.stream(entities.spliterator(), false)
                .map(e -> map(e))
                .collect(Collectors.toList()));
        ;
        return result;
    }

    @RequestMapping(value = "/api/posts/{id}", method = RequestMethod.GET)
    public VdmView getById(@PathVariable("id") int id) {
        LOGGER.info("Call to api/posts/ " + id + " received");
        VdmEntity one = repository.findOne(id);
        if (one == null) {
            throw new ResourceNotFoundException("Vdm with id = " + id + " was not found");
        }
        return map(one);
    }

    private VdmView map(VdmEntity entity) {
        VdmView v = new VdmView();
        v.setId(entity.getId());
        v.setAuthor(entity.getAuthor());
        v.setDate(entity.getPublishingDate().toString());
        v.setContent(entity.getContent());
        return v;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String s) {
            super(s);
        }
    }
}
