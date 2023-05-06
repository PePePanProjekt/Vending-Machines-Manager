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
import pp.project.vmm.endpoint.warehouse.service.dto.BatchDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchSimpleDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;

@Service
public class BatchServiceImplementation implements BatchService {

    private final BatchRepository batchRepository;
    private final HoldsRepository holdsRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public BatchServiceImplementation(BatchRepository batchRepository, HoldsRepository holdsRepository, ItemRepository itemRepository) {

        this.batchRepository = batchRepository;
        this.holdsRepository = holdsRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<BatchDetailsDTO> getAll() {
        
        List<BatchDetailsDTO> batchDetailsDtoList = new ArrayList<>();
        List<Batch> batchList = batchRepository.findAll();
        for(Batch batch : batchList) {

            List<HoldsDetailsDTO> holdsDetailsDtoList = new ArrayList<>();
            List<Holds> holdsList = batch.getHolds();
            for(Holds holds : holdsList) {

                HoldsDetailsDTO holdsDetailsDto = new HoldsDetailsDTO(
                    holds.getId(),
                    holds.getItem().getId(), 
                    holds.getItemPrice(), 
                    holds.getItemAmount()
                );
                holdsDetailsDtoList.add(holdsDetailsDto);
            }

            BatchDetailsDTO batchDetailsDto = new BatchDetailsDTO(
                batch.getId(), 
                batch.getDate(), 
                holdsDetailsDtoList
            );
            batchDetailsDtoList.add(batchDetailsDto);
        }

        return batchDetailsDtoList;
    }

    @Override
    public BatchDetailsDTO getById(UUID id) {
        
        Optional<Batch> batchOptional = batchRepository.findById(id);
        if(batchOptional.isEmpty()) {
            return null;
        }
        Batch batch = batchOptional.get();

        List<HoldsDetailsDTO> holdsDetaildDtoList = new ArrayList<>();
        List<Holds> holdsList = batch.getHolds();
        for(Holds holds : holdsList) {

            HoldsDetailsDTO holdsDetailsDto = new HoldsDetailsDTO(
                holds.getId(), 
                holds.getItem().getId(), 
                holds.getItemPrice(), 
                holds.getItemAmount()
            );
            holdsDetaildDtoList.add(holdsDetailsDto);
        }

        BatchDetailsDTO batchDetailsDto = new BatchDetailsDTO(
            batch.getId(), 
            batch.getDate(), 
            holdsDetaildDtoList
        );
        return batchDetailsDto;
    }

    @Override
    public ResponseEntity<String> updateBatch(BatchDetailsDTO detailsDTO) {
        
        Optional<Batch> batchOptional = batchRepository.findById(detailsDTO.getId());
        if(batchOptional.isEmpty()) {
            return new ResponseEntity<String>("Batch object of given id does not exist: " + detailsDTO.getId(), HttpStatus.NOT_FOUND);
        }
        Batch batch = batchOptional.get();
        batch.setDate(detailsDTO.getDate());
        
        List<Holds> holdsList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        for(HoldsDetailsDTO holdsDetailsDto : detailsDTO.getHolds()) {

            Optional<Holds> holdsOptional = holdsRepository.findById(holdsDetailsDto.getId());
            if(holdsOptional.isEmpty()) {
                return new ResponseEntity<String>("Holds object of given id does not exist: " + holdsDetailsDto.getId(), HttpStatus.NOT_FOUND);
            }
            Holds holds = holdsOptional.get();

            if(!holds.getBatch().getId().equals(batch.getId())) {
                return new ResponseEntity<String>("Holds object of given id is not associated with given Batch object: " + holdsDetailsDto, HttpStatus.BAD_REQUEST);
            }
            
            Optional<Item> itemOptional = itemRepository.findById(holdsDetailsDto.getItemId());
            if(itemOptional.isEmpty()) {
                return new ResponseEntity<String>("Item of given id does not exist: " + holdsDetailsDto.getItemId(), HttpStatus.NOT_FOUND);
            }
            Item item = itemOptional.get();

            if(holds.getItem().getId().equals(item.getId())) {
                
                int oldItemAmount = holds.getItemAmount();
                holds.setItemAmount(holdsDetailsDto.getItemAmount());
                holds.setItemPrice(holdsDetailsDto.getItemPrice());
                item.setAmountAvailable(item.getAmountAvailable() - (oldItemAmount - holds.getItemAmount()));
                itemList.add(item);
                holdsList.add(holds);
            }
            else {

                Item oldItem = holds.getItem();
                oldItem.setAmountAvailable(oldItem.getAmountAvailable() - holds.getItemAmount());
                holds.setItemAmount(holdsDetailsDto.getItemAmount());
                holds.setItemPrice(holdsDetailsDto.getItemPrice());
                holds.setItem(item);
                item.setAmountAvailable(item.getAmountAvailable() + holds.getItemAmount());
                itemList.add(oldItem);
                itemList.add(item);
                holdsList.add(holds);
            }
        }

        batchRepository.save(batch);
        for(Item item : itemList) {
            itemRepository.save(item);
        }
        for(Holds holds : holdsList) {
            holdsRepository.save(holds);
        }

        return new ResponseEntity<String>("Successfully updated Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteBatchById(UUID id) {

        Optional<Batch> batchOptional = batchRepository.findById(id);
        if(batchOptional.isEmpty()) {
            return new ResponseEntity<String>("Batch object of given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        Batch batch = batchOptional.get();

        List<Holds> holdsList = batch.getHolds();
        holdsRepository.deleteAllInBatch(holdsList);
        batchRepository.delete(batch);

        return new ResponseEntity<String>("Successfully deleted Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addBatch(BatchDetailsDTO detailsDTO) {

        Batch batch = new Batch(detailsDTO.getDate(), false);
        batch = batchRepository.save(batch);

        List<Holds> holdsList = new ArrayList<>();
        for(HoldsDetailsDTO holdsDetailsDto : detailsDTO.getHolds()) {

            Optional<Item> itemOptional = itemRepository.findById(holdsDetailsDto.getItemId());
            if(itemOptional.isEmpty()) {
                return new ResponseEntity<String>("Item object of given id does not exist: " + holdsDetailsDto.getItemId(), HttpStatus.NOT_FOUND);
            }
            Item item = itemOptional.get();

            Holds holds = new Holds(holdsDetailsDto.getItemPrice(), holdsDetailsDto.getItemAmount(), false, batch, item);
            holdsList.add(holds);
        }
        holdsRepository.saveAll(holdsList);

        return new ResponseEntity<String>("Successfully saved a new Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public List<BatchSimpleDTO> getAllSimple() {
        
        List<BatchSimpleDTO> dtoList = new ArrayList<>();
        List<Batch> batchList = batchRepository.findAll();
        for(Batch batch : batchList) {
            BatchSimpleDTO simpleDto = new BatchSimpleDTO(batch.getId(), batch.getDate(), batch.getHolds().size());
            dtoList.add(simpleDto);
        }
        return dtoList;
    }

    @Override
    public BatchSimpleDTO getSimpleById(UUID id) {

        Optional<Batch> batchOptional = batchRepository.findById(id);
        if(batchOptional.isEmpty()) {
            return null;
        }
        Batch batch = batchOptional.get();

        BatchSimpleDTO simpleDto = new BatchSimpleDTO(batch.getId(), batch.getDate(), batch.getHolds().size());
        return simpleDto;
    }

    
    
}
