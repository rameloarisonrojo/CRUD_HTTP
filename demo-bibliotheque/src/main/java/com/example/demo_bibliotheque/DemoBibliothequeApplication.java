package com.example.demo_bibliotheque;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoBibliothequeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBibliothequeApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(LivreRepository repository) {
        return args -> {
            // Ces livres seront insérés en base à CHAQUE démarrage
            repository.save(new Livre("Le Seigneur des anneaux", "J.R.R. Tolkien", 1954));
            repository.save(new Livre("1984", "George Orwell", 1949));
            repository.save(new Livre("Le Petit Prince", "Antoine de Saint-Exupéry", 1943));
            System.out.println("Base de données initialisée !");
        };
    }
}