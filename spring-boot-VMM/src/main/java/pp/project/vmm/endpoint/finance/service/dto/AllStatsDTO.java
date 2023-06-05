package pp.project.vmm.endpoint.finance.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllStatsDTO {

    // totals
    private Integer totalSales;

    private Float totalProfit;

    private Integer totalBought;

    private Float totalExpenses;

    // best / worst items
    private UUID bestSellingItemId;

    private String bestSellingItemName;

    private Integer bestSellingItemAmount;

    private UUID worstSellingItemId;

    private String worstSellingItemName;

    private Integer worstSellingItemAmount;

    // best / worst machines
    private UUID bestPerformingMachineId;

    private String bestPerformingMachineName;

    private Integer bestPerformingMachineSales;

    private UUID worstPerformingMachineId;

    private String worstPerformingMachineName;

    private Integer worstPerformingMachineSales;

    private List<ZeroSaleItem> zeroSaleItems;

    private List<ZeroSaleMachine> zeroSaleMachines;
}
