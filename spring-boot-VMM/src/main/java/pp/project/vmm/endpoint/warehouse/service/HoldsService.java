package pp.project.vmm.endpoint.warehouse.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsFullInfoDTO;

public interface HoldsService {
    
    List<HoldsFullInfoDTO> getAll();

    HoldsFullInfoDTO getById(UUID id);

    List<HoldsFullInfoDTO> getAllByItemId(UUID itemId);

    ResponseEntity<String> update (HoldsDetailsDTO detailsDTO);

    ResponseEntity<String> deleteById(UUID id);

    ResponseEntity<String> add(UUID batchId, HoldsDetailsDTO holdsDTO);

}
