package pp.project.vmm.endpoint.warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Batch;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.BatchRepository;
import pp.project.vmm.endpoint.system.repository.HoldsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchSimpleDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BatchServiceImplementationTest {

    @Mock
    HoldsRepository holdsRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BatchRepository batchRepository;
    @InjectMocks
    BatchServiceImplementation batchService;
    Item item1;
    Item item2;
    Batch batch1;
    Batch batch2;
    Holds holds1;
    Holds holds2;
    Holds holds3;
    @BeforeEach
    void init()
    {
        UUID id = UUID.randomUUID();
        item1 = new Item();
        item1.setName("item1");
        item1.setId(id);
        item1.setAmountAvailable(5);
        item1.setArchived(false);

        UUID id2 = UUID.randomUUID();

        item2 = new Item();
        item2.setName("item2");
        item2.setId(id2);
        item2.setAmountAvailable(6);
        item2.setArchived(false);

        UUID idb1 = UUID.randomUUID();
        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.YEAR, 2022);
        date1.set(Calendar.MONTH, Calendar.JANUARY);
        date1.set(Calendar.DAY_OF_MONTH, 1);

        batch1 = new Batch();
        batch1.setId(idb1);
        batch1.setArchived(false);
        batch1.setDate(date1.getTime());

        UUID idb2 = UUID.randomUUID();
        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.YEAR, 2022);
        date2.set(Calendar.MONTH, Calendar.FEBRUARY);
        date2.set(Calendar.DAY_OF_MONTH, 1);
        batch2 = new Batch();
        batch2.setId(idb2);
        batch2.setArchived(false);
        batch2.setDate(date2.getTime());

        UUID idh1 = UUID.randomUUID();
        holds1 = new Holds();
        holds1.setId(idh1);
        holds1.setItem(item1);
        holds1.setItemPrice(30.0f);
        holds1.setArchived(false);
        holds1.setBatch(batch1);
        holds1.setItemAmount(10);

        UUID idh2 = UUID.randomUUID();
        holds2 = new Holds();
        holds2.setId(idh2);
        holds2.setItem(item2);
        holds2.setItemPrice(15.0f);
        holds2.setArchived(false);
        holds2.setBatch(batch1);
        holds2.setItemAmount(11);

        UUID idh3 = UUID.randomUUID();
        holds3 = new Holds();
        holds3.setId(idh3);
        holds3.setItem(item2);
        holds3.setItemPrice(18.0f);
        holds3.setArchived(false);
        holds3.setBatch(batch2);
        holds3.setItemAmount(9);

        batch1.setHolds(Arrays.asList(holds1, holds2));
        batch2.setHolds(List.of(holds3));
    }
    @Test
    void shouldGetAllTest() {
        // Given
        given(batchRepository.findAll()).willReturn(Arrays.asList(batch1, batch2));

        // When
        List<BatchDetailsDTO> result = batchService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        BatchDetailsDTO batchDetails1 = result.get(0);
        assertEquals(batch1.getId(), batchDetails1.getId());
        assertEquals(batch1.getDate(), batchDetails1.getDate());

        List<HoldsDetailsDTO> holdsDetails1 = batchDetails1.getHolds();
        assertNotNull(holdsDetails1);
        assertEquals(2, holdsDetails1.size());

        HoldsDetailsDTO holdsDetails1_1 = holdsDetails1.get(0);
        assertEquals(holds1.getId(), holdsDetails1_1.getId());
        assertEquals(holds1.getItem().getId(), holdsDetails1_1.getItemId());
        assertEquals(holds1.getItemPrice(), holdsDetails1_1.getItemPrice());
        assertEquals(holds1.getItemAmount(), holdsDetails1_1.getItemAmount());

        HoldsDetailsDTO holdsDetails1_2 = holdsDetails1.get(1);
        assertEquals(holds2.getId(), holdsDetails1_2.getId());
        assertEquals(holds2.getItem().getId(), holdsDetails1_2.getItemId());
        assertEquals(holds2.getItemPrice(), holdsDetails1_2.getItemPrice());
        assertEquals(holds2.getItemAmount(), holdsDetails1_2.getItemAmount());

        BatchDetailsDTO batchDetails2 = result.get(1);
        assertEquals(batch2.getId(), batchDetails2.getId());
        assertEquals(batch2.getDate(), batchDetails2.getDate());

        List<HoldsDetailsDTO> holdsDetails2 = batchDetails2.getHolds();
        assertNotNull(holdsDetails2);
        assertEquals(1, holdsDetails2.size());

        HoldsDetailsDTO holdsDetails2_1 = holdsDetails2.get(0);
        assertEquals(holds3.getId(), holdsDetails2_1.getId());
        assertEquals(holds3.getItem().getId(), holdsDetails2_1.getItemId());
        assertEquals(holds3.getItemPrice(), holdsDetails2_1.getItemPrice());
        assertEquals(holds3.getItemAmount(), holdsDetails2_1.getItemAmount());
    }

    @Test
    void shouldGetByIdTest() {
        // Given
        UUID id = UUID.randomUUID();

        given(batchRepository.findById(id)).willReturn(Optional.of(batch1));

        // When
        BatchDetailsDTO result = batchService.getById(id);

        // Then
        assertNotNull(result);
        assertEquals(batch1.getId(), result.getId());
        assertEquals(batch1.getDate(), result.getDate());

        List<HoldsDetailsDTO> holdsDetails = result.getHolds();
        assertNotNull(holdsDetails);
        assertEquals(2, holdsDetails.size());

        HoldsDetailsDTO holdsDetails1 = holdsDetails.get(0);
        assertEquals(holds1.getId(), holdsDetails1.getId());
        assertEquals(holds1.getItem().getId(), holdsDetails1.getItemId());
        assertEquals(holds1.getItemPrice(), holdsDetails1.getItemPrice());
        assertEquals(holds1.getItemAmount(), holdsDetails1.getItemAmount());

        verify(batchRepository, times(1)).findById(id);
        verifyNoMoreInteractions(batchRepository);
    }

    @Test
    void shouldNotGetByIdTest() {

        // Given
        UUID id = UUID.randomUUID();

        given(batchRepository.findById(id)).willReturn(Optional.empty());

        // When
        BatchDetailsDTO result = batchService.getById(id);

        assertNull(result);

        // Then
        verify(batchRepository, times(1)).findById(id);
        verifyNoMoreInteractions(batchRepository);
    }

    @Test
    void shouldUpdateBatchTest() {
        // Given
        BatchDetailsDTO detailsDTO = new BatchDetailsDTO();
        detailsDTO.setId(batch1.getId());
        detailsDTO.setDate(batch1.getDate());

        List<HoldsDetailsDTO> holdsDetailsDTOList = new ArrayList<>();
        HoldsDetailsDTO holdsDetailsDTO1 = new HoldsDetailsDTO();
        holdsDetailsDTO1.setId(holds1.getId());
        holdsDetailsDTO1.setItemId(item1.getId());
        holdsDetailsDTO1.setItemAmount(holds1.getItemAmount());
        holdsDetailsDTO1.setItemPrice(holds1.getItemPrice());
        holdsDetailsDTOList.add(holdsDetailsDTO1);

        detailsDTO.setHolds(holdsDetailsDTOList);

        List<Holds> holdsList = new ArrayList<>();
        holdsList.add(holds1);
        batch1.setHolds(holdsList);

        given(batchRepository.findById(detailsDTO.getId())).willReturn(Optional.of(batch1));
        given(holdsRepository.findById(holdsDetailsDTO1.getId())).willReturn(Optional.of(holds1));
        given(itemRepository.findById(holdsDetailsDTO1.getItemId())).willReturn(Optional.of(item1));

        // When
        ResponseEntity<String> responseEntity = batchService.updateBatch(detailsDTO);

        // Then
        verify(batchRepository, times(1)).save(batch1);
        verify(itemRepository, times(1)).save(item1);
        verify(holdsRepository, times(1)).save(holds1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully updated Batch object and all associated Holds objects", responseEntity.getBody());

    }

    @Test
    void shouldDeleteBatchByIdTest() {
        // Given
        List<Holds> holdsList = new ArrayList<>();
        holdsList.add(holds1);
        holdsList.add(holds2);
        batch1.setHolds(holdsList);

        given(batchRepository.findById(batch1.getId())).willReturn(Optional.of(batch1));

        // When
        ResponseEntity<String> response = batchService.deleteBatchById(batch1.getId());

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted Batch object and all associated Holds objects", response.getBody());

        verify(holdsRepository, times(1)).deleteAllInBatch(holdsList);
        verify(batchRepository, times(1)).delete(batch1);
    }

    @Test
    void shouldAddBatchTest() {
        // Given
        BatchDetailsDTO detailsDTO = new BatchDetailsDTO();
        detailsDTO.setDate(batch1.getDate());
        HoldsDetailsDTO holdsDetailsDTO = new HoldsDetailsDTO();
        holdsDetailsDTO.setItemAmount(holds1.getItemAmount());
        holdsDetailsDTO.setItemPrice(holds1.getItemPrice());
        holdsDetailsDTO.setItemId(UUID.randomUUID());

        List<HoldsDetailsDTO> holdsDetailsDTOList = new ArrayList<>();
        holdsDetailsDTOList.add(holdsDetailsDTO);
        detailsDTO.setHolds(holdsDetailsDTOList);
        Batch batch = new Batch(detailsDTO.getDate(), false);

        given(batchRepository.save(batch)).willReturn(batch);
        given(itemRepository.findById(holdsDetailsDTO.getItemId())).willReturn(Optional.of(item1));
        given(holdsRepository.saveAll(List.of(holds1))).willReturn(List.of(holds1));

        // When
        ResponseEntity<String> responseEntity = batchService.addBatch(detailsDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully saved a new Batch object and all associated Holds objects", responseEntity.getBody());
    }

    @Test
    void shouldGetAllSimpleTest() {
        // Given
        List<Batch> batches = new ArrayList<>();
        batches.add(batch1);
        batches.add(batch2);
        given(batchRepository.findAll()).willReturn(batches);

        // When
        List<BatchSimpleDTO> result = batchService.getAllSimple();

        // Then
        assertEquals(2, result.size());

        assertEquals(batches.get(0).getId(), result.get(0).getId());
        assertEquals(batches.get(0).getDate(), result.get(0).getDate());

        assertEquals(batches.get(1).getId(), result.get(1).getId());
        assertEquals(batches.get(1).getDate(), result.get(1).getDate());
    }

    @Test
    void shouldGetSimpleByIdTest() {
        // given

        given(batchRepository.findById(batch1.getId())).willReturn(Optional.of(batch1));

        // when
        BatchSimpleDTO result = batchService.getSimpleById(batch1.getId());

        // then
        assertNotNull(result);
        assertEquals(batch1.getId(), result.getId());
        assertEquals(batch1.getDate(), result.getDate());
        assertEquals(batch1.getHolds().size(), result.getHoldsAmount());
    }

    @Test
    public void shouldNotGetSimpleByIdTest() {
        // given
        UUID id = UUID.randomUUID();
        given(batchRepository.findById(id)).willReturn(Optional.empty());

        // when
        BatchSimpleDTO result = batchService.getSimpleById(id);

        // then
        assertNull(result);
    }
}