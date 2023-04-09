package pp.project.vmm.endpoint.system.service;

import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;

import java.util.List;
import java.util.UUID;

public interface MachineService {

    List<VendingMachineSimpleDTO> getMachinesInfoSimple();

    VendingMachineDetailsDTO findMachineDetailsById(UUID id);

    Boolean addMachine(VendingMachineDetailsDTO detailsDTO);

    Boolean updateMachine(VendingMachineDetailsDTO detailsDTO);

    Boolean deleteMachine(VendingMachineDetailsDTO detailsDTO);

    Boolean deleteMachineById(UUID id);
}
