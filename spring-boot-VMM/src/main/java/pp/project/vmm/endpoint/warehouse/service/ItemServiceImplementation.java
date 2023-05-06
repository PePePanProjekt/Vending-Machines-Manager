package pp.project.vmm.endpoint.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.ItemSimpleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImplementation implements ItemService{

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImplementation(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public List<ItemDetailsDTO> getItems() {

        List<Item> itemList = itemRepository.findAll();
        List<ItemDetailsDTO> itemDetailsDTOList = new ArrayList<>();
        for(Item item : itemList) {
            ItemDetailsDTO detailsDTO = new ItemDetailsDTO(
                    item.getId(),
                    item.getName(),
                    item.getAmountAvailable()
            );
            itemDetailsDTOList.add(detailsDTO);
        }

        return itemDetailsDTOList;
    }

    @Override
    public ItemDetailsDTO getItemById(UUID id) {

        if(!itemRepository.existsById(id)) {
            return null;
        }

        Item item = itemRepository.findById(id).get();

        return new ItemDetailsDTO(
                item.getId(),
                item.getName(),
                item.getAmountAvailable()
        );
    }

    @Override
    public ResponseEntity<String> updateItem(ItemSimpleDTO simpleDTO) {

        if(!itemRepository.existsById(simpleDTO.getId())) {
            return new ResponseEntity<>("Item of given id does not exist", HttpStatus.NOT_FOUND);
        }

        Item item = itemRepository.findById(simpleDTO.getId()).get();
        item.setName(simpleDTO.getName());
        itemRepository.save(item);

        return new ResponseEntity<>("Successfully updated item", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addItem(ItemSimpleDTO simpleDTO) {

        Item item = new Item(simpleDTO.getName(), 0, false);
        Item dbItem = itemRepository.save(item);

        if(dbItem.getId() == null) {
            return new ResponseEntity<>("Could not create new item", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Successfully created new item", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteItemById(UUID id) {

        if(!itemRepository.existsById(id)) {
            return new ResponseEntity<>("Item of given id does not exist", HttpStatus.NOT_FOUND);
        }

        itemRepository.deleteById(id);

        return new ResponseEntity<>("Successfully deleted item", HttpStatus.OK);
    }

}
