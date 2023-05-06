package pp.project.vmm.endpoint.warehouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pp.project.vmm.endpoint.system.model.Batch;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.BatchRepository;
import pp.project.vmm.endpoint.system.repository.HoldsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsFullInfoDTO;

@Service
public class HoldsServiceImplementation implements HoldsService {

    private final HoldsRepository holdsRepository;
    private final ItemRepository itemRepository;
    private final BatchRepository batchRepository;

    @Autowired
    public HoldsServiceImplementation(HoldsRepository holdsRepository, ItemRepository itemRepository, BatchRepository batchRepository) {

        this.holdsRepository = holdsRepository;
        this.itemRepository = itemRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public List<HoldsFullInfoDTO> getAll() {
        
        List<HoldsFullInfoDTO> dtoList = new ArrayList<>();
        List<Holds> holdsList = holdsRepository.findAll();

        for(Holds holds : holdsList) {

            HoldsFullInfoDTO tempDto = new HoldsFullInfoDTO(
                holds.getId(),
                holds.getItem().getId(),
                holds.getBatch().getId(),
                holds.getItemPrice(),
                holds.getItemAmount()
                );
            dtoList.add(tempDto);

        }

        return dtoList;
    }

    @Override
    public HoldsFullInfoDTO getById(UUID id) {
        
        Optional<Holds> holdsOptional = holdsRepository.findById(id);
        if(holdsOptional.isEmpty()) {
            return null;
        }

        HoldsFullInfoDTO holdsDto = new HoldsFullInfoDTO(
            holdsOptional.get().getId(),
            holdsOptional.get().getItem().getId(),
            holdsOptional.get().getBatch().getId(),
            holdsOptional.get().getItemPrice(),
            holdsOptional.get().getItemAmount()
            );
        return holdsDto;
    }

    @Override
    public List<HoldsFullInfoDTO> getAllByItemId(UUID itemId) {

        List<HoldsFullInfoDTO> dtoList = new ArrayList<>();
        List<Holds> holdsList = holdsRepository.findByItemId(itemId);

        for(Holds holds : holdsList) {

            HoldsFullInfoDTO tempDto = new HoldsFullInfoDTO(
                holds.getId(),
                holds.getItem().getId(),
                holds.getBatch().getId(),
                holds.getItemPrice(),
                holds.getItemAmount()
                );
            dtoList.add(tempDto);

        }

        return dtoList;
    }

    @Override
    public ResponseEntity<String> update(HoldsDetailsDTO detailsDTO) {
        
        Optional<Holds> holdsOptional = holdsRepository.findById(detailsDTO.getId());
        if(holdsOptional.isEmpty()) {
            return new ResponseEntity<>("Holds object of given id does not exist", HttpStatus.NOT_FOUND);
        }
        Holds holds = holdsOptional.get();

        int oldItemAmount = holds.getItemAmount();
        holds.setItemAmount(detailsDTO.getItemAmount());
        holds.setItemPrice(detailsDTO.getItemPrice());

        Optional<Item> itemOptional = itemRepository.findById(detailsDTO.getItemId());
        if(itemOptional.isEmpty()) {
            return new ResponseEntity<String>("Item object of given id does not exist", HttpStatus.NOT_FOUND);
        }
        Item item = itemOptional.get();

        if(item.equals(holds.getItem())) {
            item.setAmountAvailable(item.getAmountAvailable() - (oldItemAmount - holds.getItemAmount()));
            holds.setItem(item);
            holdsRepository.save(holds);
            itemRepository.save(item);
        }
        else {
            Item oldItem = holds.getItem();
            oldItem.setAmountAvailable(oldItem.getAmountAvailable() - oldItemAmount);
            itemRepository.save(oldItem);
            item.setAmountAvailable(item.getAmountAvailable() + holds.getItemAmount());
            holds.setItem(item);
            holdsRepository.save(holds);
            itemRepository.save(item);
        }

        return new ResponseEntity<String>("Successfully updated Holds object", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(UUID id) {

        Optional<Holds> holdsOptional = holdsRepository.findById(id);
        if(holdsOptional.isEmpty()) {
            return new ResponseEntity<>("Holds object of given id does not exist", HttpStatus.NOT_FOUND);
        }
        Holds holds = holdsOptional.get();

        Item item = holds.getItem();
        item.setAmountAvailable(item.getAmountAvailable() - holds.getItemAmount());
        itemRepository.save(item);
        holdsRepository.deleteById(id);

        return new ResponseEntity<String>("Successfully deleted Holds object", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> add(UUID batchId, HoldsDetailsDTO holdsDTO) {
        
        Optional<Batch> batchOptional = batchRepository.findById(batchId);
        if(batchOptional.isEmpty()) {
            return new ResponseEntity<String>("Batch object of given id does not exist", HttpStatus.NOT_FOUND);
        }
        Batch batch = batchOptional.get();

        Optional<Item> itemOptional = itemRepository.findById(holdsDTO.getItemId());
        if(itemOptional.isEmpty()) {
            return new ResponseEntity<String>("Item object of given id does not exist", HttpStatus.NOT_FOUND);
        }
        Item item = itemOptional.get();

        Holds holds = new Holds(holdsDTO.getItemPrice(), holdsDTO.getItemAmount(), false, batch, item);
        holdsRepository.save(holds);

        return new ResponseEntity<String>("Successfully created a new Holds object", HttpStatus.OK);
    }
    
}
