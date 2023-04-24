package pp.project.vmm.endpoint.external.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ExternalService {

    ResponseEntity<String> sale(UUID id, int slot);
}
