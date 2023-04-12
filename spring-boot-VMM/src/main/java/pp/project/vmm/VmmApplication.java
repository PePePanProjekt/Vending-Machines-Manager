package pp.project.vmm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;

@SpringBootApplication
public class VmmApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmmApplication.class, args);
	}

}
