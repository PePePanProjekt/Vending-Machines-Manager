package ch.martinelli.demo.backend.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pp.project.vmm.security.person.Person;
import pp.project.vmm.security.person.PersonRepository;

@Component
public class PersonPopulator implements CommandLineRunner {

    private final PersonRepository personRepository;

    public PersonPopulator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) {
        Person p1 = new Person();
        p1.setName("Fred Astaire");
        personRepository.saveAndFlush(p1);

        Person p2 = new Person();
        p2.setName("Ginger Rogers");
        personRepository.saveAndFlush(p2);
    }
}
