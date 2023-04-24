package pp.project.vmm.endpoint.external.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.project.vmm.endpoint.external.service.ExternalService;

import java.util.UUID;

@RestController
@RequestMapping("/api/external")
public class ExternalRestController {

    private final ExternalService externalService;

    @Autowired
    public ExternalRestController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @PostMapping("/sale/{machineId}/{dispenser}")
    public ResponseEntity<String> makeSale(@PathVariable UUID machineId, @PathVariable int dispenser) {

        return externalService.sale(machineId, dispenser);
    }
    
}
