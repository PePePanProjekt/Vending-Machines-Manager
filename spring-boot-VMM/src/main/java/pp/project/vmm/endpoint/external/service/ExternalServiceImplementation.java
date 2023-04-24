package pp.project.vmm.endpoint.external.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.system.model.Contains;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.Sale;
import pp.project.vmm.endpoint.system.model.VendingMachine;
import pp.project.vmm.endpoint.system.repository.ContainsRepository;
import pp.project.vmm.endpoint.system.repository.SaleRepository;
import pp.project.vmm.endpoint.system.repository.VendingMachineRepository;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExternalServiceImplementation implements ExternalService{

    private final VendingMachineRepository machineRepository;

    private final ContainsRepository containsRepository;

    private final SaleRepository saleRepository;

    @Autowired
    public ExternalServiceImplementation(VendingMachineRepository machineRepository,
                                         ContainsRepository containsRepository,
                                         SaleRepository saleRepository) {

        this.machineRepository = machineRepository;
        this.containsRepository = containsRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public ResponseEntity<String> sale(UUID id, int slot) {

        Optional<VendingMachine> vendingMachine = machineRepository.findById(id);
        if(vendingMachine.isEmpty()) {
            return new ResponseEntity<>("Vending machine of given id does not exist", HttpStatus.NOT_FOUND);
        }

        List<Contains> containsList = vendingMachine.get().getContains();
        Contains containsSale = new Contains();
        containsSale.setDispenserNumber(-1);
        for(Contains contains : containsList) {
            if(contains.getDispenserNumber() == slot) {
                containsSale = contains;
                break;
            }
        }
        if(containsSale.getDispenserNumber() == -1) {
            return new ResponseEntity<>("No item present in given dispenser of vending machine", HttpStatus.NOT_FOUND);
        }

        if(containsSale.getItemAmount() == 0) {
            return new ResponseEntity<>("No items left in dispenser", HttpStatus.NOT_FOUND);
        }
        Item item = containsSale.getItem();
        containsSale.setItemAmount(containsSale.getItemAmount() - 1);
        containsRepository.save(containsSale);

        Date timestamp = java.util.Date.from(java.time.LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Sale sale = new Sale(timestamp, containsSale.getItemPrice(), false);
        sale.setItem(item);
        sale.setVendingMachine(vendingMachine.get());
        saleRepository.save(sale);

        return new ResponseEntity<>("Sale performed successfully", HttpStatus.OK);
    }

}
