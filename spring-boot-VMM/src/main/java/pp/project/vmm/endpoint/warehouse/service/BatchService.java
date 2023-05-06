package pp.project.vmm.endpoint.warehouse.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import pp.project.vmm.endpoint.warehouse.service.dto.BatchDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchSimpleDTO;

public interface BatchService {
    
    List<BatchDetailsDTO> getAll();

    BatchDetailsDTO getById(UUID id);

    ResponseEntity<String> updateBatch(BatchDetailsDTO detailsDTO);

    ResponseEntity<String> deleteBatchById(UUID id);

    ResponseEntity<String> addBatch(BatchDetailsDTO detailsDTO);

    List<BatchSimpleDTO> getAllSimple();

    BatchSimpleDTO getSimpleById(UUID id);

}
