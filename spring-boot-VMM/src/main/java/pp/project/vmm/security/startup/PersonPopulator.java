package pp.project.vmm.security.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pp.project.vmm.security.person.Person;
import pp.project.vmm.security.person.PersonRepository;

@Component
@RequiredArgsConstructor
public class PersonPopulator implements CommandLineRunner {

    private final PersonRepository personRepository;

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
