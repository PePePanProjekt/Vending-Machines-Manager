package pp.project.vmm.endpoint.system.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;
import pp.project.vmm.endpoint.system.service.dto.VendingMachineSimpleDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void getMachineInfoById() {
    }

    @Test
    void addMachine() {
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