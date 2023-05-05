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

import pp.project.vmm.endpoint.warehouse.service.ItemService;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemSimpleDTO;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseRestController {

    private final ItemService itemService;

    @Autowired
    public WarehouseRestController(ItemService itemService) {

        this.itemService = itemService;
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
