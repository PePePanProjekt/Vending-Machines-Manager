package pp.project.vmm.endpoint.system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.system.service.ItemService;
import pp.project.vmm.endpoint.system.service.MachineService;
import pp.project.vmm.endpoint.system.service.dto.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/management")
public class ManagementRestController {

    private final MachineService machineService;

    private final ItemService itemService;

    @Autowired
    public ManagementRestController(MachineService machineService, ItemService itemService) {
        this.machineService = machineService;
        this.itemService = itemService;
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


    // Item CRUD endpoints
    @GetMapping("/items")
    public List<ItemDetailsDTO> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/items/{itemId}")
    public ItemDetailsDTO getItemById(@PathVariable UUID itemId) {
        return itemService.getItemById(itemId);
    }

    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody ItemSimpleDTO simpleDTO) {

        ResponseEntity<String> response = itemService.addItem(simpleDTO);
        return response;
    }

    @PutMapping("/items")
    public ResponseEntity<String> updateItem(@RequestBody ItemSimpleDTO simpleDTO) {

        ResponseEntity<String> response = itemService.updateItem(simpleDTO);
        return response;
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable UUID itemId) {

        ResponseEntity<String> response = itemService.deleteItemById(itemId);
        return response;
    }

}
