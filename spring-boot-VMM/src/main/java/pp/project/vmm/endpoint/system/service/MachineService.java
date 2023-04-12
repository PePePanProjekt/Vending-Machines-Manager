package pp.project.vmm.endpoint.system.service;

import org.springframework.http.ResponseEntity;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineFullInfoDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSlotDTO;

import java.util.List;
import java.util.UUID;

public interface MachineService {

    List<VendingMachineSimpleDTO> getMachinesInfoSimple();

    VendingMachineFullInfoDTO getMachineInfoById(UUID id);

    ResponseEntity<String> addMachine(VendingMachineDetailsDTO detailsDTO);

    ResponseEntity<String> updateMachine(VendingMachineDetailsDTO detailsDTO);

    ResponseEntity<String> refillMachine(UUID id, List<VendingMachineSlotDTO> slotDTOList);

    ResponseEntity<String> deleteMachine(VendingMachineDetailsDTO detailsDTO);

    ResponseEntity<String> deleteMachineById(UUID id);
}
