package pp.project.vmm.endpoint.finance.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleItemStatsDTO {

    private UUID itemId;

    private String itemName;

    private Integer amountLeft;

    private Integer totalSales;

    private Float totalProfit;

    private Integer totalBought;

    private Float totalExpenses;
}
