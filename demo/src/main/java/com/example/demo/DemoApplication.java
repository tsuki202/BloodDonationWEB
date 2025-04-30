package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "controller", "service", "view"})
@EntityScan(basePackages = {"entity"})
@EnableJpaRepositories(basePackages = {"repository"})
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
<<<<<<< HEAD
		System.out.println("🩸 Система донорства крові запущена як веб-додаток");
=======

		// Запуск консольної програми після ініціалізації Spring context
		System.out.println("🩸 Система донорства крові запущена");
		new ConsoleApplication().run();
>>>>>>> 54d3cb371fd3135584cb9fa0bf3ccab36d79fee5
	}
}