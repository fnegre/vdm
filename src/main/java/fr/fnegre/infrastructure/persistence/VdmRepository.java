package fr.fnegre.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface VdmRepository extends JpaRepository<VdmEntity, Integer>, JpaSpecificationExecutor<VdmEntity> {
    List<VdmEntity> findByAuthor(String author);
    List<VdmEntity> findByPublishingDateBetween(LocalDate from, LocalDate to);
    List<VdmEntity> findByAuthorAndPublishingDateBetween(String author, LocalDate from, LocalDate to);
}
