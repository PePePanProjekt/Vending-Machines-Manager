package pp.project.vmm.endpoint.warehouse.service.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoldsFullInfoDTO {

    private UUID id;

    private UUID itemId;

    private UUID batchId;

    private Float itemPrice;

    private Integer itemAmount;
    
}
