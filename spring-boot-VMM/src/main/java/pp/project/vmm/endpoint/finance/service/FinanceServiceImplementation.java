package pp.project.vmm.endpoint.finance.service;

import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.finance.service.dto.*;
import pp.project.vmm.endpoint.system.model.Batch;
import pp.project.vmm.endpoint.system.model.Holds;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.model.Sale;
import pp.project.vmm.endpoint.system.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImplementation implements FinanceService {

    private ItemRepository itemRepository;
    private VendingMachineRepository vendingMachineRepository;
    private SaleRepository saleRepository;
    private BatchRepository batchRepository;

    public FinanceServiceImplementation(ItemRepository itemRepository,
                                        VendingMachineRepository vendingMachineRepository,
                                        SaleRepository saleRepository,
                                        BatchRepository batchRepository) {
        this.itemRepository = itemRepository;
        this.vendingMachineRepository = vendingMachineRepository;
        this.saleRepository = saleRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public SingleItemStatsDTO getSingleItemStats(Date startDate, Date endDate, UUID itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if(itemOptional.isEmpty()) {
            return null;
        }
        Item item = itemOptional.get();
        List<Sale> saleList = saleRepository.findByItemId(item.getId())
                .stream()
                .filter(x -> x.getSaleTime().compareTo(startDate) <= 0 && x.getSaleTime().compareTo(endDate) >= 0)
                .toList();
        List<Batch> batchList = batchRepository.findAll()
                .stream()
                .filter(x -> x.getDate().compareTo(startDate) <= 0 && x.getDate().compareTo(endDate) >= 0)
                .toList();

        float totalProfit = (float)saleList.stream().mapToDouble(Sale::getPrice).sum();
        int totalSold = saleList.size();
        float totalExpenses = 0;
        int totalBought = 0;
        for(Batch batch : batchList) {
            List<Holds> holdsList = batch.getHolds()
                    .stream()
                    .filter(x -> x.getItem().getId().equals(item.getId()))
                    .toList();
            for(Holds holds : holdsList) {
                totalExpenses += holds.getItemAmount() * holds.getItemPrice();
                totalBought += holds.getItemAmount();
            }
        }
        return new SingleItemStatsDTO(
                item.getId(),
                item.getName(),
                item.getAmountAvailable(),
                totalSold,
                totalProfit,
                totalBought,
                totalExpenses
        );
    }

    @Override
    public SingleMachineStatsDTO getSingleMachineStats(Date startDate, Date endDate, UUID machineId) {

        return null;
    }

    @Override
    public AllStatsDTO getAllStats(Date startDate, Date endDate) {
        return null;
    }
}
