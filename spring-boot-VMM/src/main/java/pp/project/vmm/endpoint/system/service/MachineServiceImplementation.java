package pp.project.vmm.endpoint.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineFullInfoDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSlotDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MachineServiceImplementation implements MachineService {

    private final VendingMachineRepository vendingMachineRepository;
    private final ContainsRepository containsRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public MachineServiceImplementation(VendingMachineRepository vendingMachineRepository,
                                        ContainsRepository containsRepository,
                                        ItemRepository itemRepository) {

        this.vendingMachineRepository = vendingMachineRepository;
        this.containsRepository = containsRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<VendingMachineSimpleDTO> getMachinesInfoSimple() {

        List<VendingMachineSimpleDTO> vendingMachineSimpleDTOList = new ArrayList<>();
        List<VendingMachine> vendingMachineList = vendingMachineRepository.findAll();

        for(VendingMachine vendingMachine : vendingMachineList) {
            int usedSlots = 0;
            int totalSlots = vendingMachine.getDispenserAmount() * vendingMachine.getDispenserDepth();
            int percentSlotsUsed;

            List<Contains> containsList = vendingMachine.getContains();
            for(Contains contains : containsList) {
                usedSlots += contains.getItemAmount();
            }

            percentSlotsUsed = (usedSlots / totalSlots) * 100;
            VendingMachineSimpleDTO vendingMachineSimpleDTO = new VendingMachineSimpleDTO(
                    vendingMachine.getId(),
                    vendingMachine.getLocation(),
                    vendingMachine.getName(),
                    percentSlotsUsed);
            vendingMachineSimpleDTOList.add(vendingMachineSimpleDTO);
        }

        return vendingMachineSimpleDTOList;
    }

    @Override
    public VendingMachineFullInfoDTO getMachineInfoById(UUID id) {

        if(vendingMachineRepository.findById(id).isEmpty()) {
            return null;
        }

        VendingMachine vendingMachine = vendingMachineRepository.findById(id).get();
        VendingMachineDetailsDTO detailsDTO = new VendingMachineDetailsDTO(
                vendingMachine.getId(),
                vendingMachine.getLocation(),
                vendingMachine.getName(),
                vendingMachine.getDispenserAmount(),
                vendingMachine.getDispenserDepth()
        );

        List<VendingMachineSlotDTO> slotDTOList = new ArrayList<>();
        List<Contains> containsList = vendingMachine.getContains();
        for(Contains contains : containsList) {

            Item item = contains.getItem();
            VendingMachineSlotDTO slotDTO = new VendingMachineSlotDTO(
                    contains.getDispenserNumber(),
                    item.getId(),
                    item.getName(),
                    contains.getItemPrice(),
                    contains.getItemAmount()
            );
            slotDTOList.add(slotDTO);
        }

        return new VendingMachineFullInfoDTO(detailsDTO, slotDTOList);
    }

    @Override
    public ResponseEntity<String> addMachine(VendingMachineDetailsDTO detailsDTO) {

        VendingMachine vendingMachine = new VendingMachine(detailsDTO.getLocation(), detailsDTO.getName(), detailsDTO.getDispenserAmount(), detailsDTO.getDispenserDepth());
        VendingMachine dbVendingMachine = vendingMachineRepository.save(vendingMachine);

        if(dbVendingMachine.getId() == null) {
            return new ResponseEntity<>("Could not create vending machine", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Successfully created vending machine", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> updateMachine(VendingMachineDetailsDTO detailsDTO) {

        if(vendingMachineRepository.findById(detailsDTO.getId()).isEmpty()) {
            return new ResponseEntity<>("Vending machine of given id does not exist", HttpStatus.NOT_FOUND);
        }

        VendingMachine vendingMachine = new VendingMachine(detailsDTO.getLocation(), detailsDTO.getName(), detailsDTO.getDispenserAmount(), detailsDTO.getDispenserDepth());
        vendingMachine.setId(detailsDTO.getId());
        VendingMachine dbVendingMachine = vendingMachineRepository.save(vendingMachine);

        if(dbVendingMachine.getId() == null) {
            return new ResponseEntity<>("Could not update given vending machine", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Successfully updated vending machine", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> refillMachine(UUID id, List<VendingMachineSlotDTO> slotDTOList) {

        // Check if all items given exist
        if(!vendingMachineRepository.existsById(id)) {
            return new ResponseEntity<>("Vending machine of given id does not exist", HttpStatus.NOT_FOUND);
        }
        for(VendingMachineSlotDTO slotDTO : slotDTOList) {
            if(!itemRepository.existsById(slotDTO.getItemId())) {
                return new ResponseEntity<>("Item of given id does not exist", HttpStatus.NOT_FOUND);
            }
        }

        // Get list of currently used dispensers
        if(vendingMachineRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("This error should have no way of happening", HttpStatus.BAD_REQUEST);
        }
        VendingMachine vendingMachine = vendingMachineRepository.findById(id).get();
        List<Contains> containsList = vendingMachine.getContains();
        List<Integer> usedDispensers = new ArrayList<>();
        for(Contains contains : containsList) {
            usedDispensers.add(contains.getDispenserNumber());
        }

        // Create or update Contains entities to mach new data
        for(VendingMachineSlotDTO slotDTO : slotDTOList) {
            if(usedDispensers.contains(slotDTO.getSlotNumber())) {

                // Check the correctness of given data;
                if(vendingMachine.getDispenserDepth() < slotDTO.getItemAmount()) {
                    return new ResponseEntity<>("Given item amount too big for given dispenser", HttpStatus.BAD_REQUEST);
                }

                // Get contains object matching the current slotDTO
                Contains contains = containsList.stream()
                        .filter(item -> item.getDispenserNumber().equals(slotDTO.getSlotNumber()))
                        .toList()
                        .get(0);

                // Update contains information for the current dispenser
                if(itemRepository.findById(slotDTO.getItemId()).isEmpty()) {
                    return new ResponseEntity<>("This error should have no way of happening", HttpStatus.BAD_REQUEST);
                }
                Item item = itemRepository.findById(slotDTO.getItemId()).get();
                contains.setItem(item);
                contains.setItemPrice(slotDTO.getItemPrice());
                contains.setItemAmount(slotDTO.getItemAmount());
                containsRepository.save(contains);

            }
            else {

                // Check the correctness of given data;
                if(vendingMachine.getDispenserAmount() < slotDTO.getSlotNumber()) {
                    return new ResponseEntity<>("Dispenser of given number does not exist", HttpStatus.BAD_REQUEST);
                }
                if(vendingMachine.getDispenserDepth() < slotDTO.getItemAmount()) {
                    return new ResponseEntity<>("Given item amount too big for given dispenser", HttpStatus.BAD_REQUEST);
                }

                // Create and save a new contains entity
                Item item = itemRepository.findById(slotDTO.getItemId()).get();
                Contains contains = new Contains(
                        slotDTO.getItemPrice(),
                        slotDTO.getItemAmount(),
                        slotDTO.getSlotNumber(),
                        vendingMachine,
                        item
                );
                containsRepository.save(contains);

            }
        }

        return new ResponseEntity<>("Vending machine refilled correctly", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteMachine(VendingMachineDetailsDTO detailsDTO) {

        if(vendingMachineRepository.findById(detailsDTO.getId()).isEmpty()) {
            return new ResponseEntity<>("Vending machine of given id does not exist", HttpStatus.NOT_FOUND);
        }
        vendingMachineRepository.deleteById(detailsDTO.getId());

        return new ResponseEntity<>("Successfully deleted vending machine", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteMachineById(UUID id) {

        if(vendingMachineRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("Vending machine of given id does not exist", HttpStatus.NOT_FOUND);
        }
        vendingMachineRepository.deleteById(id);

        return new ResponseEntity<>("Successfully deleted vending machine", HttpStatus.OK);
    }

}
