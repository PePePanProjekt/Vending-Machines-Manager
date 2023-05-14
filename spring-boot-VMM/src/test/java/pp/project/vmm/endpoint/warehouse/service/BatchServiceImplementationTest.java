package pp.project.vmm.endpoint.warehouse.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Batch;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.BatchRepository;
import pp.project.vmm.endpoint.system.repository.HoldsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.warehouse.service.dto.BatchDetailsDTO;
import pp.project.vmm.endpoint.warehouse.service.dto.HoldsDetailsDTO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
    UUID id = UUID.randomUUID();
    @Test
    void shouldGetAllTest() {
        // Given
        Item item1 = new Item();
        item1.setName("item1");
        item1.setId(id);
        item1.setAmountAvailable(5);
        item1.setArchived(false);

        UUID id2 = UUID.randomUUID();

        Item item2 = new Item();
        item2.setName("item2");
        item2.setId(id2);
        item2.setAmountAvailable(6);
        item2.setArchived(false);

        UUID idb1 = UUID.randomUUID();
        Date date1 = new Date(2023, Calendar.FEBRUARY, 14);
        Batch batch1 = new Batch();
        batch1.setId(idb1);
        batch1.setArchived(false);
        batch1.setDate(date1);

        UUID idb2 = UUID.randomUUID();
        Date date2 = new Date(2023, Calendar.JANUARY, 14);
        Batch batch2 = new Batch();
        batch2.setId(idb2);
        batch2.setArchived(false);
        batch2.setDate(date2);

        UUID idh1 = UUID.randomUUID();
        Holds holds1 = new Holds();
        holds1.setId(idh1);
        holds1.setItem(item1);
        holds1.setItemPrice(30.0f);
        holds1.setArchived(false);
        holds1.setBatch(batch1);
        holds1.setItemAmount(10);

        UUID idh2 = UUID.randomUUID();
        Holds holds2 = new Holds();
        holds2.setId(idh2);
        holds2.setItem(item2);
        holds2.setItemPrice(15.0f);
        holds2.setArchived(false);
        holds2.setBatch(batch1);
        holds2.setItemAmount(11);

        UUID idh3 = UUID.randomUUID();
        Holds holds3 = new Holds();
        holds3.setId(idh3);
        holds3.setItem(item2);
        holds3.setItemPrice(18.0f);
        holds3.setArchived(false);
        holds3.setBatch(batch2);
        holds3.setItemAmount(9);

        batch1.setHolds(Arrays.asList(holds1, holds2));
        batch2.setHolds(List.of(holds3));

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
    void getById() {
    }

    @Test
    void updateBatch() {
    }

    @Test
    void deleteBatchById() {
    }

    @Test
    void addBatch() {
    }

    @Test
    void getAllSimple() {
    }

    @Test
    void getSimpleById() {
    }
}