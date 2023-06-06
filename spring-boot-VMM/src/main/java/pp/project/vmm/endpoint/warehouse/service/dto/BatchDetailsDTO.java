package pp.project.vmm.endpoint.warehouse.service.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class BatchDetailsDTO {

    private UUID id;

    @NonNull
    private Date date;

    private String name;

    @NonNull
    private List<HoldsDetailsDTO> holds;
    
}
