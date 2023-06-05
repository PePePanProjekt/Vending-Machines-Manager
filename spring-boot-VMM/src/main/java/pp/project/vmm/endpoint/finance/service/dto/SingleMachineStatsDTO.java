package pp.project.vmm.endpoint.finance.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleMachineStatsDTO {

    private UUID machineId;

    private String machineName;

    private Integer itemsSold;

    private Float totalProfit;

    private UUID bestSellingItemId;

    private String bestSellingItemName;

    private Integer bestSellingItemAmount;

    private UUID worstSellingItemId;

    private String worstSellingItemName;

    private Integer worstSellingItemAmount;

    private List<ZeroSaleItem> zeroSaleItems;
}
