package pp.project.vmm.endpoint.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;

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
            VendingMachineSimpleDTO vendingMachineSimpleDTO = new VendingMachineSimpleDTO(vendingMachine.getLocation(), vendingMachine.getName(), percentSlotsUsed);
            vendingMachineSimpleDTOList.add(vendingMachineSimpleDTO);
        }

        return vendingMachineSimpleDTOList;
    }

    @Override
    public VendingMachineDetailsDTO findMachineDetailsById(UUID id) {

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

        return detailsDTO;
    }

    @Override
    public Boolean addMachine(VendingMachineDetailsDTO detailsDTO) {

        VendingMachine vendingMachine = new VendingMachine(detailsDTO.getLocation(), detailsDTO.getName(), detailsDTO.getDispenserAmount(), detailsDTO.getDispenserDepth());
        VendingMachine dbVendingMachine = vendingMachineRepository.save(vendingMachine);

        return dbVendingMachine.getId() != null;
    }

    @Override
    public Boolean updateMachine(VendingMachineDetailsDTO detailsDTO) {

        if(vendingMachineRepository.findById(detailsDTO.getId()).isEmpty()) {
            return false;
        }

        VendingMachine vendingMachine = new VendingMachine(detailsDTO.getLocation(), detailsDTO.getName(), detailsDTO.getDispenserAmount(), detailsDTO.getDispenserDepth());
        vendingMachine.setId(detailsDTO.getId());
        VendingMachine dbVendingMachine = vendingMachineRepository.save(vendingMachine);

        return dbVendingMachine.getId() != null;
    }

    @Override
    public Boolean deleteMachine(VendingMachineDetailsDTO detailsDTO) {

        if(vendingMachineRepository.findById(detailsDTO.getId()).isEmpty()) {
            return false;
        }
        vendingMachineRepository.deleteById(detailsDTO.getId());

        return true;
    }

    @Override
    public Boolean deleteMachineById(UUID id) {

        if(vendingMachineRepository.findById(id).isEmpty()) {
            return false;
        }
        vendingMachineRepository.deleteById(id);

        return true;
    }

}
