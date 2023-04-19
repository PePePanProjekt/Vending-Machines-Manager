package pp.project.vmm.endpoint.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailsDTO {

    private UUID id;

    private String name;

    private int amountAvailable;
}
