package pp.project.vmm.endpoint.warehouse.service;

import org.springframework.http.ResponseEntity;

import pp.project.vmm.endpoint.warehouse.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemSimpleDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemDetailsDTO> getItems();

    ItemDetailsDTO getItemById(UUID id);

    ResponseEntity<String> updateItem(ItemSimpleDTO simpleDTO);

    ResponseEntity<String> addItem(ItemSimpleDTO simpleDTO);

    ResponseEntity<String> deleteItemById(UUID id);

}
