package pp.project.vmm.endpoint.external.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.Sale;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.SaleRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ExternalServiceImplementationTest {
    @Mock
    private VendingMachineRepository machineRepository;

    @Mock
    private ContainsRepository containsRepository;

    @Mock
    private SaleRepository saleRepository;
    @InjectMocks
    ExternalServiceImplementation externalService;
    UUID id;
    VendingMachine vendingMachine;
    int slot = 1;
    @BeforeEach
    void init()
    {
        id = UUID.randomUUID();
        vendingMachine = new VendingMachine();
        vendingMachine.setId(id);
    }
    @Test
    public void shouldSale() {
        // Given
        List<Contains> containsList = new ArrayList<>();
        Contains contains = new Contains();
        contains.setItem(new Item());
        contains.setDispenserNumber(slot);
        contains.setItemAmount(1);
        contains.setItemPrice(1.5f);
        containsList.add(contains);
        vendingMachine.setContains(containsList);
        given(machineRepository.findById(id)).willReturn(Optional.of(vendingMachine));

        // When
        ResponseEntity<String> response = externalService.sale(id, slot);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sale performed successfully", response.getBody());
        assertEquals(0, contains.getItemAmount());
        verify(containsRepository, times(1)).save(contains);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void shouldNotSaleInvalidVendingMachine() {
        // Given
        given(machineRepository.findById(id)).willReturn(Optional.empty());

        // When
        ResponseEntity<String> response = externalService.sale(id, slot);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Vending machine of given id does not exist", response.getBody());
        verify(containsRepository, never()).save(any(Contains.class));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    public void shouldNotSaleInvalidDispenserNumber() {
        // Given
        List<Contains> containsList = new ArrayList<>();
        Contains contains = new Contains();
        contains.setDispenserNumber(slot + 1);
        containsList.add(contains);
        vendingMachine.setContains(containsList);
        when(machineRepository.findById(id)).thenReturn(Optional.of(vendingMachine));

        // When
        ResponseEntity<String> response = externalService.sale(id, slot);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No item present in given dispenser of vending machine", response.getBody());
        verify(containsRepository, never()).save(any(Contains.class));
        verify(saleRepository, never()).save(any(Sale.class));
    }

}