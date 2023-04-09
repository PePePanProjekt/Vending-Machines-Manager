package pp.project.vmm.endpoint.system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.system.service.MachineService;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management-api")
public class ManagementRestController {

    private MachineService machineService;

    @Autowired
    public ManagementRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/machines")
    public List<VendingMachineSimpleDTO> getMachines() {
        return machineService.getMachinesInfoSimple();
    }

    @GetMapping("/machines/{machineId}")
    public VendingMachineDetailsDTO getMachineById(@PathVariable UUID machineId) {

        VendingMachineDetailsDTO detailsDTO = machineService.findMachineDetailsById(machineId);
        if(detailsDTO == null) {
            throw new RuntimeException("Vending machine of given id " + machineId + " does not exist");
        }

        return detailsDTO;
    }

    @PostMapping("/machines")
    public String createMachine(@RequestBody VendingMachineDetailsDTO detailsDTO) {

        Boolean status = machineService.addMachine(detailsDTO);
        if(status) {
            return "Successfully created new vending machine";
        } else {
            return "Could not create new vending machine";
        }
    }

    @PutMapping("/machines")
    public String updateMachine(@RequestBody VendingMachineDetailsDTO detailsDTO) {

        Boolean status = machineService.updateMachine(detailsDTO);

        if(status) {
            return "Successfully updated vending machine information";
        } else {
            return "Could not update vending machine information";
        }
    }

    @DeleteMapping("/machines/{machineId}")
    public String deleteMachine(@PathVariable UUID machineId) {

        Boolean status = machineService.deleteMachineById(machineId);

        if(status) {
            return "Successfully deleted vending machine";
        } else {
            return "Could not delete vending machine";
        }
    }

}
