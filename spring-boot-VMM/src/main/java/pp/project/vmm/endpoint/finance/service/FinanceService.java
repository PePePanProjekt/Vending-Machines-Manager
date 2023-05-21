package pp.project.vmm.endpoint.finance.service;

import pp.project.vmm.endpoint.finance.service.dto.*;

import java.util.Date;
import java.util.UUID;

public interface FinanceService {

    SingleItemStatsDTO getSingleItemStats(Date startDate, Date endDate, UUID itemId);

    SingleMachineStatsDTO getSingleMachineStats(Date startDate, Date endDate, UUID machineId);

    AllStatsDTO getAllStats(Date startDate, Date endDate);
}
