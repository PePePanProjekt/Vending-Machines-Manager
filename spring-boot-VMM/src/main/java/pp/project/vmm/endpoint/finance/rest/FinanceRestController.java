package pp.project.vmm.endpoint.finance.rest;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.finance.service.FinanceService;
import pp.project.vmm.endpoint.finance.service.dto.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/finance")
public class FinanceRestController {

    private FinanceService financeService;

    @Autowired
    private FinanceRestController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/items/{itemId}")
    public SingleItemStatsDTO getSingleItemStats(@PathVariable UUID itemId, @RequestBody StatsRequestDTO requestDTO) {
        return financeService.getSingleItemStats(requestDTO.getStartDate(), requestDTO.getEndDate(), itemId);
    }

    @GetMapping("/machines/{machineId}")
    public SingleMachineStatsDTO getSingleMachineStats(@PathVariable UUID machineId, @RequestBody StatsRequestDTO requestDTO) {
        return financeService.getSingleMachineStats(requestDTO.getStartDate(), requestDTO.getEndDate(), machineId);
    }

    @GetMapping("/all")
    public AllStatsDTO getAllStats(@RequestBody StatsRequestDTO requestDTO) {
        return financeService.getAllStats(requestDTO.getStartDate(), requestDTO.getEndDate());
    }
}
