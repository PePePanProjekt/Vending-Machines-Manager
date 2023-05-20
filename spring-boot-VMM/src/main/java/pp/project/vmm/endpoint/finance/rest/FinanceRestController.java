package pp.project.vmm.endpoint.finance.rest;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.finance.service.dto.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/finance")
public class FinanceRestController {

    @GetMapping("/items/{itemId}")
    public SingleItemStatsDTO getSingleItemStats(@PathVariable UUID itemId, @RequestBody StatsRequestDTO requestDTO) {
        throw new NotImplementedException( this.getClass().getSimpleName() + " / getSingleItemStats method not yet implemented");
    }

    @GetMapping("/machines/{machineId}")
    public SingleMachineStatsDTO getSingleMachineStats(@PathVariable UUID machineId, @RequestBody StatsRequestDTO requestDTO) {
        throw new NotImplementedException( this.getClass().getSimpleName() + " / getSingleMachineStats method not yet implemented");
    }

    @GetMapping("/all")
    public AllStatsDTO getAllStats(@RequestBody StatsRequestDTO requestDTO) {
        throw new NotImplementedException( this.getClass().getSimpleName() + " / getAllStats method not yet implemented");
    }
}
