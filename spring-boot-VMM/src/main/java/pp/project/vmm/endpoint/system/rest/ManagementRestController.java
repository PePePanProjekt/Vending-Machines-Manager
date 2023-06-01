package pp.project.vmm.endpoint.system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pp.project.vmm.endpoint.system.service.MachineService;
import pp.project.vmm.endpoint.system.service.dto.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/management")
public class ManagementRestController {

    private final MachineService machineService;

    @Autowired
    public ManagementRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    // Machine CRUD endpoints
    @GetMapping("/machines")
    public List<VendingMachineSimpleDTO> getMachines() {
        return machineService.getMachinesInfoSimple();
    }

    @GetMapping("/machines/{machineId}")
    public VendingMachineFullInfoDTO getMachineById(@PathVariable UUID machineId) {

        VendingMachineFullInfoDTO infoDTO = machineService.getMachineInfoById(machineId);
        if(infoDTO == null) {
            throw new RuntimeException("Vending machine of given id " + machineId + " does not exist");
        }

        return infoDTO;
    }

    @PostMapping("/machines")
    public ResponseEntity<String> createMachine(@RequestBody VendingMachineDetailsDTO detailsDTO) {

        ResponseEntity<String> response = machineService.addMachine(detailsDTO);
        return response;
    }

    @PutMapping("/machines")
    public ResponseEntity<String> updateMachine(@RequestBody VendingMachineDetailsDTO detailsDTO) {

        ResponseEntity<String> response = machineService.updateMachine(detailsDTO);
        return response;
    }

    @PutMapping("/machines/{machineId}")
    public ResponseEntity<String> refillMachine(@PathVariable UUID machineId, @RequestBody List<VendingMachineSlotDTO> slotDTOList) {

        ResponseEntity<String> response = machineService.refillMachine(machineId, slotDTOList);
        return response;
    }

    @DeleteMapping("/machines/{machineId}")
    public ResponseEntity<String> deleteMachine(@PathVariable UUID machineId) {

        ResponseEntity<String> response = machineService.deleteMachineById(machineId);
        return response;
    }

}
