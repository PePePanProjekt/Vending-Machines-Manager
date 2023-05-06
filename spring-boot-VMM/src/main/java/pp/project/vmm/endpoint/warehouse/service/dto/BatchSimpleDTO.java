package pp.project.vmm.endpoint.warehouse.service.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchSimpleDTO {

    private UUID id;

    private Date date;

    private Integer holdsAmount;
    
}
