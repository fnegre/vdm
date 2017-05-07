package fr.fnegre;

import fr.fnegre.domain.service.VdmDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(VdmApplication.class, args);
	}
}
