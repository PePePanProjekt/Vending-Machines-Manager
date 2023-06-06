package pp.project.vmm.endpoint.warehouse.service;

import java.util.*;

import lombok.NonNull;
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
        for (Batch batch : batchList) {

            List<HoldsDetailsDTO> holdsDetailsDtoList = new ArrayList<>();
            List<Holds> holdsList = batch.getHolds();
            for (Holds holds : holdsList) {

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
                    batch.getName(),
                    holdsDetailsDtoList
            );
            batchDetailsDtoList.add(batchDetailsDto);
        }

        return batchDetailsDtoList;
    }

    @Override
    public BatchDetailsDTO getById(UUID id) {

        Optional<Batch> batchOptional = batchRepository.findById(id);
        if (batchOptional.isEmpty()) {
            return null;
        }
        Batch batch = batchOptional.get();

        List<HoldsDetailsDTO> holdsDetaildDtoList = new ArrayList<>();
        List<Holds> holdsList = batch.getHolds();
        for (Holds holds : holdsList) {

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
                batch.getName(),
                holdsDetaildDtoList
        );
        return batchDetailsDto;
    }

    @Override
    public ResponseEntity<String> updateBatch(BatchDetailsDTO detailsDTO) {

        Optional<Batch> batchOptional = batchRepository.findById(detailsDTO.getId());
        if (batchOptional.isEmpty()) {
            return new ResponseEntity<String>("Batch object of given id does not exist: " + detailsDTO.getId(), HttpStatus.NOT_FOUND);
        }
        Batch batch = batchOptional.get();
        batch.setDate(detailsDTO.getDate());
        batch.setName(detailsDTO.getName());

        List<UUID> holdsIdList = detailsDTO.getHolds().stream().map(HoldsDetailsDTO::getId).toList();
        List<HoldsDetailsDTO> holdsToDelete = batch.getHolds().stream()
                .filter(x -> !(holdsIdList.contains(x.getId())) && x.getId() != null)
                .map(x -> new HoldsDetailsDTO(
                        x.getId(),
                        x.getItem().getId(),
                        x.getItemPrice(),
                        x.getItemAmount()
                ))
                .toList();
        List<HoldsDetailsDTO> holdsToKeep = detailsDTO.getHolds().stream().filter(x -> holdsIdList.contains(x.getId()) || x.getId() == null).toList();

        Map<Item, Integer> itemAmounts = new HashMap<>();
        for(HoldsDetailsDTO holdsDetailsDTO : holdsToDelete) {
            Holds holds = holdsRepository.findById(holdsDetailsDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Holds object of given id does not exist"));
            Item item = holds.getItem();
            itemAmounts.merge(item, item.getAmountAvailable(), Integer::sum);
        }
        List<Item> itemsToReturn = new ArrayList<>();
        if(!itemAmounts.isEmpty()) {
            for(Map.Entry<Item, Integer> itemAmount : itemAmounts.entrySet()) {
                if(itemAmount.getKey().getAmountAvailable() < itemAmount.getValue()) {
                    return new ResponseEntity<>("Unable to delete hold if item it contains is already in use", HttpStatus.BAD_REQUEST);
                }
                Item item = itemAmount.getKey();
                item.setAmountAvailable(item.getAmountAvailable() - itemAmount.getValue());
                itemsToReturn.add(item);
            }
        }
        itemRepository.saveAll(itemsToReturn);
        for(HoldsDetailsDTO holdsDetailsDTO : holdsToDelete) {
            Holds holds = holdsRepository.findById(holdsDetailsDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Holds item of given id does not exist"));
            holdsRepository.delete(holds);
        }

        List<Holds> holdsList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        for (HoldsDetailsDTO holdsDetailsDto : holdsToKeep) {

            Optional<Item> itemOptional = itemRepository.findById(holdsDetailsDto.getItemId());
            if (itemOptional.isEmpty()) {
                return new ResponseEntity<String>("Item of given id does not exist: " + holdsDetailsDto.getItemId(), HttpStatus.NOT_FOUND);
            }
            Item item = itemOptional.get();


            if (holdsDetailsDto.getId() == null) {
                Holds holds = new Holds(holdsDetailsDto.getItemPrice(), holdsDetailsDto.getItemAmount(), false, batch, item);
                holds = holdsRepository.save(holds);
                if (holds.getId() == null) {
                    return new ResponseEntity<>("Unsuccessful creation of new Holds object", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                holdsDetailsDto.setId(holds.getId());
            }

            Optional<Holds> holdsOptional = holdsRepository.findById(holdsDetailsDto.getId());
            if (holdsOptional.isEmpty()) {
                return new ResponseEntity<String>("Holds object of given id does not exist: " + holdsDetailsDto.getId(), HttpStatus.NOT_FOUND);
            }
            Holds holds = holdsOptional.get();

            if (!holds.getBatch().getId().equals(batch.getId())) {
                return new ResponseEntity<String>("Holds object of given id is not associated with given Batch object: " + holdsDetailsDto, HttpStatus.BAD_REQUEST);
            }

            if (holds.getItem().getId().equals(item.getId())) {

                int oldItemAmount = holds.getItemAmount();
                holds.setItemAmount(holdsDetailsDto.getItemAmount());
                holds.setItemPrice(holdsDetailsDto.getItemPrice());
                item.setAmountAvailable(item.getAmountAvailable() - (oldItemAmount - holds.getItemAmount()));
            } else {

                Item oldItem = holds.getItem();
                oldItem.setAmountAvailable(oldItem.getAmountAvailable() - holds.getItemAmount());
                holds.setItemAmount(holdsDetailsDto.getItemAmount());
                holds.setItemPrice(holdsDetailsDto.getItemPrice());
                holds.setItem(item);
                item.setAmountAvailable(item.getAmountAvailable() + holds.getItemAmount());
                itemList.add(oldItem);
            }
            itemList.add(item);
            holdsList.add(holds);
        }

        batchRepository.save(batch);
        holdsRepository.saveAll(holdsList);
        itemRepository.saveAll(itemList);

        return new ResponseEntity<String>("Successfully updated Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteBatchById(UUID id) {

        Optional<Batch> batchOptional = batchRepository.findById(id);
        if (batchOptional.isEmpty()) {
            return new ResponseEntity<String>("Batch object of given id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        Batch batch = batchOptional.get();

        List<Holds> holdsList = batch.getHolds();
        Map<Item, Integer> itemAmounts = new HashMap<>();
        for(Holds holds : holdsList) {
            Item item = holds.getItem();
            itemAmounts.merge(item, holds.getItemAmount(), Integer::sum);
        }
        if(!itemAmounts.isEmpty()) {
            for(Map.Entry<Item , Integer> itemAmount : itemAmounts.entrySet()) {
                if(itemAmount.getKey().getAmountAvailable() < itemAmount.getValue()) {
                    return new ResponseEntity<>("Unable to delete batch if items it contains are already in use", HttpStatus.BAD_REQUEST);
                }
            }
        }
        for(Holds holds : holdsList) {
            Item item = holds.getItem();
            item.setAmountAvailable(item.getAmountAvailable() - holds.getItemAmount());
            itemRepository.save(item);
        }
        holdsRepository.deleteAllInBatch(holdsList);
        batchRepository.delete(batch);

        return new ResponseEntity<String>("Successfully deleted Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addBatch(BatchDetailsDTO detailsDTO) {

        Batch batch = new Batch(detailsDTO.getDate(),detailsDTO.getName(), false);
        batch = batchRepository.save(batch);

        List<Item> itemList = new ArrayList<>();
        List<Holds> holdsList = new ArrayList<>();
        for (HoldsDetailsDTO holdsDetailsDto : detailsDTO.getHolds()) {

            Optional<Item> itemOptional = itemRepository.findById(holdsDetailsDto.getItemId());
            if (itemOptional.isEmpty()) {
                return new ResponseEntity<String>("Item object of given id does not exist: " + holdsDetailsDto.getItemId(), HttpStatus.NOT_FOUND);
            }
            Item item = itemOptional.get();
            item.setAmountAvailable(item.getAmountAvailable() + holdsDetailsDto.getItemAmount());
            itemList.add(item);

            Holds holds = new Holds(holdsDetailsDto.getItemPrice(), holdsDetailsDto.getItemAmount(), false, batch, item);
            holdsList.add(holds);
        }
        holdsRepository.saveAll(holdsList);
        itemRepository.saveAll(itemList);

        return new ResponseEntity<String>("Successfully saved a new Batch object and all associated Holds objects", HttpStatus.OK);
    }

    @Override
    public List<BatchSimpleDTO> getAllSimple() {

        List<BatchSimpleDTO> dtoList = new ArrayList<>();
        List<Batch> batchList = batchRepository.findAll();
        for (Batch batch : batchList) {
            BatchSimpleDTO simpleDto = new BatchSimpleDTO(batch.getId(), batch.getDate(), batch.getName(), batch.getHolds().size());
            dtoList.add(simpleDto);
        }
        return dtoList;
    }

    @Override
    public BatchSimpleDTO getSimpleById(UUID id) {

        Optional<Batch> batchOptional = batchRepository.findById(id);
        if (batchOptional.isEmpty()) {
            return null;
        }
        Batch batch = batchOptional.get();

        BatchSimpleDTO simpleDto = new BatchSimpleDTO(batch.getId(), batch.getDate(),batch.getName(), batch.getHolds().size());
        return simpleDto;
    }


}
