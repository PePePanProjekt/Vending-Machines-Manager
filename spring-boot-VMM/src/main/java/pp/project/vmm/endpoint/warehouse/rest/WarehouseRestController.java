package pp.project.vmm.endpoint.warehouse.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pp.project.vmm.endpoint.warehouse.service.BatchService;
import pp.project.vmm.endpoint.warehouse.service.HoldsService;
import pp.project.vmm.endpoint.warehouse.service.ItemService;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchSimpleDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsFullInfoDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemSimpleDTO;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseRestController {

    private final ItemService itemService;
    private final BatchService batchService;
    private final HoldsService holdsService;

    @Autowired
    public WarehouseRestController(ItemService itemService, BatchService batchService, HoldsService holdsService) {

        this.itemService = itemService;
        this.batchService = batchService;
        this.holdsService = holdsService;
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

    // Batch CRUD endpoints
    @GetMapping("/batch")
    public List<BatchDetailsDTO> getAllBatch() {
        return batchService.getAll();
    }

    @GetMapping("/batch/{batchId}")
    public BatchDetailsDTO getBatchById(@PathVariable UUID batchId) {
        return batchService.getById(batchId);
    }

    @GetMapping("/batch/simple")
    public List<BatchSimpleDTO> getAllSimpleBatch() {
        return batchService.getAllSimple();
    }

    @GetMapping("/batch/simple/{batchId}")
    public BatchSimpleDTO getSimpleBatchById(@PathVariable UUID batchId) {
        return batchService.getSimpleById(batchId);
    }

    @PostMapping("/batch")
    public ResponseEntity<String> createBatch(@RequestBody BatchDetailsDTO detailsDTO) {
        ResponseEntity<String> response = batchService.addBatch(detailsDTO);
        return response;
    }

    @PutMapping("/batch")
    public ResponseEntity<String> updateBatch(@RequestBody BatchDetailsDTO detailsDTO) {
        ResponseEntity<String> response = batchService.updateBatch(detailsDTO);
        return response;
    }

    @DeleteMapping("/batch/{batchId}")
    public ResponseEntity<String> deleteBatch(@PathVariable UUID batchId) {
        ResponseEntity<String> response = batchService.deleteBatchById(batchId);
        return response;
    }

    @GetMapping("/batch/holds")
    public List<HoldsFullInfoDTO> getAllHolds() {
        return holdsService.getAll();
    }

    @GetMapping("/batch/holds/{holdsId}")
    public HoldsFullInfoDTO getHoldsById(@PathVariable UUID holdsId) {
        return holdsService.getById(holdsId);
    }

    @GetMapping("/batch/holds/items/{itemId}") 
    public List<HoldsFullInfoDTO> getHoldsByItemId(@PathVariable UUID itemId) {
        return holdsService.getAllByItemId(itemId);
    }

    @PostMapping("/batch/holds/{batchId}")
    public ResponseEntity<String> createHolds(@PathVariable UUID batchId, @RequestBody HoldsDetailsDTO detailsDTO) {
        ResponseEntity<String> response = holdsService.add(batchId, detailsDTO);
        return response;
    }

    @PutMapping("/batch/holds")
    public ResponseEntity<String> updateHolds(@RequestBody HoldsDetailsDTO detailsDTO) {
        ResponseEntity<String> response = holdsService.update(detailsDTO);
        return response;
    }

    @DeleteMapping("/batch/holds/{holdsId}")
    public ResponseEntity<String> deleteHolds(@PathVariable UUID holdsId) {
        ResponseEntity<String> response = holdsService.deleteById(holdsId);
        return response;
    }
    
}
