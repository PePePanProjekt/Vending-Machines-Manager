package pp.project.vmm.endpoint.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineFullInfoDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSlotDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class MachineServiceImplementationTest {
    @Mock
    VendingMachineRepository vendingMachineRepository;

    @Mock
    ContainsRepository containsRepository;

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    MachineServiceImplementation vendingMachineService;


    UUID id = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();

    @Test
    public void shouldGetMachinesInfoSimple() {
        // Given
        VendingMachine vendingMachine1 = new VendingMachine();
        vendingMachine1.setId(id);
        vendingMachine1.setLocation("Location1");
        vendingMachine1.setName("Machine1");
        vendingMachine1.setDispenserAmount(5);
        vendingMachine1.setDispenserDepth(10);
        Contains contains1 = new Contains();
        Contains contains2 = new Contains();
        contains1.setItemAmount(20);
        contains2.setItemAmount(10);
        vendingMachine1.setContains(Arrays.asList(contains1, contains2));

        VendingMachine vendingMachine2 = new VendingMachine();
        vendingMachine2.setId(id2);
        vendingMachine2.setLocation("Location2");
        vendingMachine2.setName("Machine2");
        vendingMachine2.setDispenserAmount(3);
        vendingMachine2.setDispenserDepth(8);
        Contains contains3 = new Contains();
        Contains contains4 = new Contains();
        contains3.setItemAmount(15);
        contains4.setItemAmount(30);
        vendingMachine2.setContains(Arrays.asList(contains3, contains4));

        List<VendingMachine> vendingMachineList = Arrays.asList(vendingMachine1, vendingMachine2);

        given(vendingMachineRepository.findAll()).willReturn(vendingMachineList);

        // When
        List<VendingMachineSimpleDTO> result = vendingMachineService.getMachinesInfoSimple();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        VendingMachineSimpleDTO result1 = result.get(0);
        assertEquals(id, result1.getId());
        assertEquals("Location1", result1.getLocation());
        assertEquals("Machine1", result1.getName());


        VendingMachineSimpleDTO result2 = result.get(0);
        assertEquals(id, result2.getId());
        assertEquals("Location1", result2.getLocation());
        assertEquals("Machine1", result2.getName());


        VendingMachineSimpleDTO result3 = result.get(1);
        assertEquals(id2, result3.getId());
        assertEquals("Location2", result3.getLocation());
        assertEquals("Machine2", result3.getName());


        VendingMachineSimpleDTO result4 = result.get(1);
        assertEquals(id2, result4.getId());
        assertEquals("Location2", result4.getLocation());
        assertEquals("Machine2", result4.getName());
    }

    @Test
    public void shouldGetMachineInfoById() {
        // Given
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setId(id);
        vendingMachine.setLocation("Location1");
        vendingMachine.setName("Machine1");
        vendingMachine.setDispenserAmount(5);
        vendingMachine.setDispenserDepth(10);

        Contains contains1 = new Contains();
        contains1.setDispenserNumber(1);
        contains1.setItemPrice(1.5f);
        contains1.setItemAmount(20);
        Item item1 = new Item();
        item1.setId(UUID.randomUUID());
        item1.setName("Item1");
        contains1.setItem(item1);

        Contains contains2 = new Contains();
        contains2.setDispenserNumber(2);
        contains2.setItemPrice(2.0f);
        contains2.setItemAmount(10);
        Item item2 = new Item();
        item2.setId(UUID.randomUUID());
        item2.setName("Item2");
        contains2.setItem(item2);

        vendingMachine.setContains(Arrays.asList(contains1, contains2));

        given(vendingMachineRepository.findById(id)).willReturn(Optional.of(vendingMachine));

        // When
        VendingMachineFullInfoDTO result = vendingMachineService.getMachineInfoById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getDetails().getId());
        assertEquals("Location1", result.getDetails().getLocation());
        assertEquals("Machine1", result.getDetails().getName());
        assertEquals(5, result.getDetails().getDispenserAmount());
        assertEquals(10, result.getDetails().getDispenserDepth());

        List<VendingMachineSlotDTO> slotDTOList = result.getSlots();
        assertNotNull(slotDTOList);
        assertEquals(2, slotDTOList.size());

        VendingMachineSlotDTO result1 = slotDTOList.get(0);
        assertEquals(1, result1.getSlotNumber());
        assertEquals(item1.getId(), result1.getItemId());
        assertEquals("Item1", result1.getItemName());
        assertEquals(1.5, result1.getItemPrice(), 0.001);
        assertEquals(20, result1.getItemAmount());

        VendingMachineSlotDTO result2 = slotDTOList.get(1);
        assertEquals(2, result2.getSlotNumber());
        assertEquals(item2.getId(), result2.getItemId());
        assertEquals("Item2", result2.getItemName());
        assertEquals(2.0, result2.getItemPrice(), 0.001);
        assertEquals(10, result2.getItemAmount());
    }

    @Test
    public void shouldGetMachineInfoByIdWithNonExistingId() {
        // Given
        given(vendingMachineRepository.findById(id)).willReturn(Optional.empty());

        // When
        VendingMachineFullInfoDTO result = vendingMachineService.getMachineInfoById(id);

        // Then
        assertNull(result);
    }


    @Test
    public void shouldAddMachine() {
        // Given
        VendingMachineDetailsDTO detailsDTO = new VendingMachineDetailsDTO();
        detailsDTO.setId(id);
        detailsDTO.setLocation("Location1");
        detailsDTO.setName("Machine1");
        detailsDTO.setDispenserAmount(5);
        detailsDTO.setDispenserDepth(10);

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setId(id2);
        vendingMachine.setLocation("Location1");
        vendingMachine.setName("Machine1");
        vendingMachine.setDispenserAmount(5);
        vendingMachine.setDispenserDepth(10);

        given(vendingMachineRepository.save(any(VendingMachine.class))).willReturn(vendingMachine);

        // When
        ResponseEntity<String> result = vendingMachineService.addMachine(detailsDTO);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Successfully created vending machine", result.getBody());
    }

    @Test
    public void shouldNotAddMachineWith() {
        // Given
        VendingMachineDetailsDTO detailsDTO = new VendingMachineDetailsDTO();
        detailsDTO.setId(id);
        detailsDTO.setLocation("Location1");
        detailsDTO.setName("Machine1");
        detailsDTO.setDispenserAmount(5);
        detailsDTO.setDispenserDepth(10);
        VendingMachine dbVendingMachine = new VendingMachine();
        dbVendingMachine.setId(null);

        given(vendingMachineRepository.save(any(VendingMachine.class))).willReturn(dbVendingMachine);

        // When
        ResponseEntity<String> result = vendingMachineService.addMachine(detailsDTO);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Could not create vending machine", result.getBody());
    }

    @Test
    void updateMachine() {
    }

    @Test
    void refillMachine() {
    }

    @Test
    void deleteMachine() {
    }

    @Test
    void deleteMachineById() {
    }
}