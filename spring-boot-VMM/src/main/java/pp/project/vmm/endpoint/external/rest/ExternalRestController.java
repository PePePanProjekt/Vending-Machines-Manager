package pp.project.vmm.endpoint.external.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.external.service.ExternalService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/external")
public class ExternalRestController {

    private final ExternalService externalService;

    @Autowired
    public ExternalRestController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/sale/{machineId}/{dispenser}")
    public ResponseEntity<String> makeSale(@PathVariable UUID machineId, @PathVariable int dispenser) {

        return externalService.sale(machineId, dispenser);
    }

}
