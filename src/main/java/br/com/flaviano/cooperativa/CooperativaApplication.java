package br.com.flaviano.cooperativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class CooperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativaApplication.class, args);
	}

}
