package pp.project.vmm.endpoint.warehouse.service.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class HoldsDetailsDTO {

    private UUID id;

    @NonNull
    private UUID itemId;

    @NonNull
    private Float itemPrice;

    @NonNull
    private Integer itemAmount;
    
}
