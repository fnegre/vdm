package fr.fnegre.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VdmRepository extends CrudRepository<VdmEntity, Integer> {
    List<VdmEntity> findByAuthor(String author);
    List<VdmEntity> findByPublishingDateBetween(Date from, Date to);
    List<VdmEntity> findByAuthorAndPublishingDateBetween(String author, Date from, Date to);
}
