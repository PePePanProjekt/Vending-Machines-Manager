package pp.project.vmm.endpoint.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.project.vmm.endpoint.finance.service.dto.*;
import pp.project.vmm.endpoint.system.model.*;
import pp.project.vmm.endpoint.system.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImplementation implements FinanceService {

    private final ItemRepository itemRepository;
    private final VendingMachineRepository vendingMachineRepository;
    private final SaleRepository saleRepository;
    private final BatchRepository batchRepository;

    @Autowired
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
                .filter(x -> x.getSaleTime().compareTo(startDate) >= 0 && x.getSaleTime().compareTo(endDate) <= 0)
                .toList();
        List<Batch> batchList = batchRepository.findAll()
                .stream()
                .filter(x -> x.getDate().compareTo(startDate) >= 0 && x.getDate().compareTo(endDate) <= 0)
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
        Optional<VendingMachine> vendingMachineOptional = vendingMachineRepository.findById(machineId);
        if(vendingMachineOptional.isEmpty()) {
            return null;
        }
        VendingMachine vendingMachine = vendingMachineOptional.get();
        List<Sale> saleList = saleRepository.findByMachineId(vendingMachine.getId())
                .stream()
                .filter(x -> x.getSaleTime().compareTo(startDate) >= 0 && x.getSaleTime().compareTo(endDate) <= 0)
                .toList();

        int totalSold = saleList.size();
        float totalProfit = 0;
        Map<UUID, Integer> itemPerformance = new HashMap<>();
        for(Sale sale : saleList) {
            totalProfit += sale.getPrice();
            itemPerformance.merge(sale.getItem().getId(), 1, Integer::sum);
        }
        Map.Entry<UUID, Integer> worstItemEntry = Collections.min(itemPerformance.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<UUID, Integer> bestItemEntry = Collections.max(itemPerformance.entrySet(), Map.Entry.comparingByValue());
        Optional<Item> worstItemOptional = itemRepository.findById(worstItemEntry.getKey());
        Optional<Item> bestItemOptional = itemRepository.findById(bestItemEntry.getKey());

        return new SingleMachineStatsDTO(
                vendingMachine.getId(),
                vendingMachine.getLocation() + " / " + vendingMachine.getName(),
                totalSold,
                totalProfit,
                bestItemEntry.getKey(),
                bestItemOptional.map(Item::getName).orElse("Error getting item name"),
                bestItemEntry.getValue(),
                worstItemEntry.getKey(),
                worstItemOptional.map(Item::getName).orElse("Error getting item name"),
                worstItemEntry.getValue()
        );
    }

    @Override
    public AllStatsDTO getAllStats(Date startDate, Date endDate) {
        List<Sale> saleList = saleRepository.findAll()
                .stream()
                .filter(x -> x.getSaleTime().compareTo(startDate) >= 0 && x.getSaleTime().compareTo(endDate) <= 0)
                .toList();
        List<Batch> batchList = batchRepository.findAll()
                .stream()
                .filter(x -> x.getDate().compareTo(startDate) >= 0 && x.getDate().compareTo(endDate) <= 0)
                .toList();

        int totalSales = saleList.size();
        float totalProfit = (float)saleList.stream()
                .mapToDouble(Sale::getPrice)
                .sum();
        int totalBought = 0;
        float totalExpenses = 0;
        for(Batch batch : batchList) {
            totalBought += batch.getHolds()
                    .stream()
                    .mapToInt(Holds::getItemAmount)
                    .sum();
            totalExpenses += batch.getHolds()
                    .stream()
                    .mapToDouble(x -> x.getItemPrice() * x.getItemAmount())
                    .sum();
        }

        Map<UUID, Integer> itemPerformance = new HashMap<>();
        Map<UUID, Integer> machinePerformance = new HashMap<>();
        for(Sale sale : saleList) {
            itemPerformance.merge(sale.getItem().getId(), 1, Integer::sum);
            machinePerformance.merge(sale.getVendingMachine().getId(), 1, Integer::sum);
        }
        Map.Entry<UUID, Integer> bestItemEntry = Collections.max(itemPerformance.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<UUID, Integer> worstItemEntry = Collections.min(itemPerformance.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<UUID, Integer> bestMachineEntry = Collections.max(machinePerformance.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<UUID, Integer> worstMachineEntry = Collections.min(machinePerformance.entrySet(), Map.Entry.comparingByValue());
        Optional<Item> bestItemOptional = itemRepository.findById(bestItemEntry.getKey());
        Optional<Item> worstItemOptional = itemRepository.findById(worstItemEntry.getKey());
        Optional<VendingMachine> bestMachineOptional = vendingMachineRepository.findById(bestMachineEntry.getKey());
        Optional<VendingMachine> worstMachineOptional = vendingMachineRepository.findById(worstMachineEntry.getKey());

        return new AllStatsDTO(
                totalSales,
                totalProfit,
                totalBought,
                totalExpenses,
                bestItemEntry.getKey(),
                bestItemOptional.map(Item::getName).orElse("Error getting item name"),
                bestItemEntry.getValue(),
                worstItemEntry.getKey(),
                worstItemOptional.map(Item::getName).orElse("Error getting item name"),
                worstItemEntry.getValue(),
                bestMachineEntry.getKey(),
                bestMachineOptional.map(x -> x.getLocation() + " / " + x.getName()).orElse("Error getting vending machine name"),
                bestMachineEntry.getValue(),
                worstMachineEntry.getKey(),
                worstMachineOptional.map(x -> x.getLocation() + " / " + x.getName()).orElse("Error getting vending machine name"),
                worstMachineEntry.getValue()
        );
    }
}
