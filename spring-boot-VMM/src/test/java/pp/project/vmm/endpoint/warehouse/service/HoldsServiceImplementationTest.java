package pp.project.vmm.endpoint.warehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Batch;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.BatchRepository;
import pp.project.vmm.endpoint.system.repository.HoldsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsFullInfoDTO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class HoldsServiceImplementationTest {
    @Mock
    HoldsRepository holdsRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BatchRepository batchRepository;
    @InjectMocks
    HoldsServiceImplementation holdsService;

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
        List<Holds> holdsList = Arrays.asList(holds1, holds2);

        given(holdsRepository.findAll()).willReturn(holdsList);

        // When
        List<HoldsFullInfoDTO> result = holdsService.getAll();

        // Then
        verify(holdsRepository, times(1)).findAll();
        assertEquals(2, result.size());

        HoldsFullInfoDTO dto1 = result.get(0);
        assertEquals(holds1.getId(), dto1.getId());
        assertEquals(item1.getId(), dto1.getItemId());
        assertEquals(batch1.getId(), dto1.getBatchId());
        assertEquals(holds1.getItemPrice(), dto1.getItemPrice());
        assertEquals(holds1.getItemAmount(), dto1.getItemAmount());

        HoldsFullInfoDTO dto2 = result.get(1);
        assertEquals(holds2.getId(), dto2.getId());
        assertEquals(item2.getId(), dto2.getItemId());
        assertEquals(batch1.getId(), dto2.getBatchId());
        assertEquals(holds2.getItemPrice(), dto2.getItemPrice());
        assertEquals(holds2.getItemAmount(), dto2.getItemAmount());
    }

    @Test
    void shouldGetByIdTest() {
        // Given
        given(holdsRepository.findById(holds1.getId())).willReturn(Optional.of(holds1));

        // When
        HoldsFullInfoDTO result = holdsService.getById(holds1.getId());

        // Then
        verify(holdsRepository, times(1)).findById(holds1.getId());

        assertNotNull(result);
        assertEquals(holds1.getId(), result.getId());
        assertEquals(holds1.getItem().getId(), result.getItemId());
        assertEquals(holds1.getBatch().getId(), result.getBatchId());
        assertEquals(holds1.getItemPrice(), result.getItemPrice());
        assertEquals(holds1.getItemAmount(), result.getItemAmount());
    }
    @Test
    void shouldNotGetByIdTest() {
        // Given
        UUID id = UUID.randomUUID();

        given(holdsRepository.findById(id)).willReturn(Optional.empty());

        // When
        HoldsFullInfoDTO result = holdsService.getById(id);

        // Then
        verify(holdsRepository, times(1)).findById(id);

        assertNull(result);
    }

    @Test
    void shouldGetAllByItemIdTest() {
        // Given
        List<Holds> holdsList = new ArrayList<>();
        holdsList.add(holds1);

        given(holdsRepository.findByItemId(item1.getId())).willReturn(holdsList);

        // When
        List<HoldsFullInfoDTO> dtoList = holdsService.getAllByItemId(item1.getId());

        // Then
        assertEquals(1, dtoList.size());

        HoldsFullInfoDTO dto1 = dtoList.get(0);
        assertEquals(holds1.getId(), dto1.getId());
        assertEquals(holds1.getItem().getId(), dto1.getItemId());
        assertEquals(holds1.getBatch().getId(), dto1.getBatchId());
        assertEquals(holds1.getItemPrice(), dto1.getItemPrice());
        assertEquals(holds1.getItemAmount(), dto1.getItemAmount());

        verify(holdsRepository, times(1)).findByItemId(item1.getId());
        verifyNoMoreInteractions(holdsRepository);
    }

    @Test
    void shouldUpdateTest() {
        // Given
        HoldsDetailsDTO detailsDTO = new HoldsDetailsDTO();
        detailsDTO.setId(holds1.getId());
        detailsDTO.setItemId(item1.getId());
        detailsDTO.setItemAmount(item1.getAmountAvailable());
        detailsDTO.setItemPrice(20.0f);

        given(holdsRepository.findById(holds1.getId())).willReturn(Optional.of(holds1));
        given(itemRepository.findById(item1.getId())).willReturn(Optional.of(item1));

        // When
        ResponseEntity<String> response = holdsService.update(detailsDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated Holds object", response.getBody());

        verify(holdsRepository, times(1)).findById(holds1.getId());
        verify(itemRepository, times(1)).findById(item1.getId());
        verify(holdsRepository, times(1)).save(holds1);
        verify(itemRepository, times(1)).save(item1);
    }

    @Test
    void shouldDeleteByIdTest() {

        given(holdsRepository.findById(holds1.getId())).willReturn(Optional.of(holds1));

        ResponseEntity<String> response = holdsService.deleteById(holds1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted Holds object", response.getBody());

        verify(holdsRepository, times(1)).findById(holds1.getId());
        verify(holdsRepository, times(1)).deleteById(holds1.getId());
        verify(itemRepository, times(1)).save(item1);
    }

    @Test
    void shouldAddTest() {
        UUID batchId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        HoldsDetailsDTO holdsDTO = new HoldsDetailsDTO();
        holdsDTO.setItemPrice(10.0f);
        holdsDTO.setItemAmount(5);
        holdsDTO.setItemId(itemId);

        given(batchRepository.findById(batchId)).willReturn(Optional.of(batch1));
        given(itemRepository.findById(itemId)).willReturn(Optional.of(item1));

        ResponseEntity<String> response = holdsService.add(batchId, holdsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully created a new Holds object", response.getBody());

        Holds expectedHolds = new Holds(10.0f, 5, false, batch1, item1);
        verify(holdsRepository, times(1)).save(expectedHolds);
    }
}