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

import java.util.*;

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

            if(totalSlots > 0) {
                percentSlotsUsed = (int)(((float)usedSlots / (float)totalSlots) * 100);
            }
            else {
                percentSlotsUsed = 0;
            }

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

        VendingMachine vendingMachine = new VendingMachine(detailsDTO.getLocation(), detailsDTO.getName(), detailsDTO.getDispenserAmount(), detailsDTO.getDispenserDepth(), false);
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
        VendingMachine vendingMachine = vendingMachineRepository.findById(detailsDTO.getId())
                .orElseThrow(() -> new RuntimeException("Vending machine of given id does not exist"));
        List<Contains> containsList = vendingMachine.getContains();
        for(Contains contains : containsList) {
            if(contains.getItemAmount() > detailsDTO.getDispenserDepth()) {
                return new ResponseEntity<>("Vending machine dispenser depth lower than currently loaded item amount", HttpStatus.BAD_REQUEST);
            }
        }
        vendingMachine.setLocation(detailsDTO.getLocation());
        vendingMachine.setName(detailsDTO.getName());
        vendingMachine.setDispenserAmount(detailsDTO.getDispenserAmount());
        vendingMachine.setDispenserDepth(detailsDTO.getDispenserDepth());
        vendingMachineRepository.save(vendingMachine);
        return new ResponseEntity<>("Successfully updated vending machine information", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> refillMachine(UUID id, List<VendingMachineSlotDTO> slotDTOList) {
        VendingMachine vendingMachine = vendingMachineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vending machine of given id does not exist"));

        // Remove old Contains objects and add their item amounts to the total
        List<Contains> currentContains = vendingMachine.getContains();
        for(Contains contains : currentContains) {
            Item item = contains.getItem();
            item.setAmountAvailable(item.getAmountAvailable() + contains.getItemAmount());
            itemRepository.save(item);
        }
        containsRepository.deleteAllInBatch(currentContains);

        // Create new contains objects and subtract their item amount from the total
        List<Contains> newContains = new ArrayList<>();
        Map<Item, Integer> itemAmounts = new HashMap<>();
        for(VendingMachineSlotDTO slotDTO : slotDTOList) {
            if(slotDTO.getItemAmount() > vendingMachine.getDispenserDepth()) {
                return new ResponseEntity<>("Item amount too large for this vending machine" + slotDTO, HttpStatus.BAD_REQUEST);
            }
            if(slotDTO.getSlotNumber() > vendingMachine.getDispenserAmount()) {
                return new ResponseEntity<>("Dispenser does not exist in this vending machine" + slotDTO, HttpStatus.BAD_REQUEST);
            }
            Item item = itemRepository.findById(slotDTO.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item of given id does not exist " + slotDTO.getItemId()));
            itemAmounts.merge(item, slotDTO.getItemAmount(), Integer::sum);
            Contains contains = new Contains(
                    slotDTO.getItemPrice(),
                    slotDTO.getItemAmount(),
                    slotDTO.getSlotNumber(),
                    false,
                    vendingMachine,
                    item
            );
            newContains.add(contains);
        }
        List<Item> updatedItems = new ArrayList<>();
        if(!itemAmounts.isEmpty()) {
            for(Map.Entry<Item, Integer> itemAmount : itemAmounts.entrySet()) {
                if(itemAmount.getKey().getAmountAvailable() < itemAmount.getValue()) {
                    return new ResponseEntity<>("Given item amount greater than total reserves", HttpStatus.BAD_REQUEST);
                }
                Item item = itemAmount.getKey();
                item.setAmountAvailable(item.getAmountAvailable() - itemAmount.getValue());
                updatedItems.add(item);
            }
        }
        itemRepository.saveAll(updatedItems);
        containsRepository.saveAll(newContains);
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
