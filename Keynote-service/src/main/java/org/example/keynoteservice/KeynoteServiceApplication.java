package org.example.keynoteservice;

import org.example.keynoteservice.Repository.KeynoteRepository;
import org.example.keynoteservice.entity.Keynote;
import org.example.keynoteservice.DTO.KeynoteDTO;
import org.example.keynoteservice.service.KeynoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(KeynoteRepository keynoteRepository) {
        return args -> {
            keynoteRepository.save(new Keynote(null, "Khadir", "Saad", "saad@example.com", "Speaker"));
            keynoteRepository.save(new Keynote(null, "Doe", "John", "john.doe@example.com", "Engineer"));
            keynoteRepository.save(new Keynote(null, "Smith", "Anna", "anna.smith@example.com", "Researcher"));

            keynoteRepository.findAll().forEach(k -> {
                System.out.println(k.getNom());
                System.out.println(k.getPrenom());
                System.out.println(k.getEmail());
                System.out.println(k.getFonction());
            });
        };
    }

    @Bean
    CommandLineRunner commandLineRunnerUsingService(KeynoteService keynoteService) {
        return args -> {
            KeynoteDTO dto1 = new KeynoteDTO();
            dto1.setNom("ServiceKhadir");
            dto1.setPrenom("Saad");
            dto1.setEmail("saad.service@example.com");
            dto1.setFonction("Speaker");

            KeynoteDTO dto2 = new KeynoteDTO();
            dto2.setNom("ServiceDoe");
            dto2.setPrenom("John");
            dto2.setEmail("john.service@example.com");
            dto2.setFonction("Engineer");

            KeynoteDTO dto3 = new KeynoteDTO();
            dto3.setNom("ServiceSmith");
            dto3.setPrenom("Anna");
            dto3.setEmail("anna.service@example.com");
            dto3.setFonction("Researcher");

            KeynoteDTO created1 = keynoteService.create(dto1);
            KeynoteDTO created2 = keynoteService.create(dto2);
            KeynoteDTO created3 = keynoteService.create(dto3);

            System.out.println("Created via service IDs: " + created1.getId() + ", " + created2.getId() + ", " + created3.getId());

            keynoteService.getAll().forEach(k -> {
                System.out.println("Service list -> " + k.getNom() + " " + k.getPrenom() + " (" + k.getEmail() + ") - " + k.getFonction());
            });
        };
    }

}
