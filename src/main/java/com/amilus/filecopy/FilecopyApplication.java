package com.amilus.filecopy;

import com.amilus.filecopy.controller.FileCopyController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FilecopyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilecopyApplication.class, args);
	}
}
