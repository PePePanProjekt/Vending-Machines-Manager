package pp.project.vmm.endpoint.finance.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.endpoint.finance.service.FinanceService;
import pp.project.vmm.endpoint.finance.service.dto.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/finance")
public class FinanceRestController {

    private final DateTimeFormatter dateTimeFormatter;
    private final FinanceService financeService;

    @Autowired
    private FinanceRestController(FinanceService financeService) {
        this.financeService = financeService;
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @GetMapping("/items/{itemId}/{startDateString}/{endDateString}")
    @SecurityRequirement(name = "Bearer Authentication")
    public SingleItemStatsDTO getSingleItemStats(@PathVariable UUID itemId, @PathVariable String startDateString, @PathVariable String endDateString) {
        LocalDate localStartDate = LocalDate.parse(startDateString, dateTimeFormatter);
        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        LocalDate localEndDate = LocalDate.parse(endDateString, dateTimeFormatter);
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        return financeService.getSingleItemStats(startDate, endDate, itemId);
    }

    @GetMapping("/machines/{machineId}/{startDateString}/{endDateString}")
    @SecurityRequirement(name = "Bearer Authentication")
    public SingleMachineStatsDTO getSingleMachineStats(@PathVariable UUID machineId, @PathVariable String startDateString, @PathVariable String endDateString) {
        LocalDate localStartDate = LocalDate.parse(startDateString, dateTimeFormatter);
        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        LocalDate localEndDate = LocalDate.parse(endDateString, dateTimeFormatter);
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        return financeService.getSingleMachineStats(startDate, endDate, machineId);
    }

    @GetMapping("/all/{startDateString}/{endDateString}")
    @SecurityRequirement(name = "Bearer Authentication")
    public AllStatsDTO getAllStats(@PathVariable String startDateString, @PathVariable String endDateString) {
        LocalDate localStartDate = LocalDate.parse(startDateString, dateTimeFormatter);
        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        LocalDate localEndDate = LocalDate.parse(endDateString, dateTimeFormatter);
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());
        return financeService.getAllStats(startDate, endDate);
    }
}
