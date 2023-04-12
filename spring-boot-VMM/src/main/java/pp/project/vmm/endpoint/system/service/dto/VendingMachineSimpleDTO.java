package pp.project.vmm.endpoint.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO used to get basic vending machine information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendingMachineSimpleDTO {

    private UUID id;

    private String location;

    private String name;

    private int status;
}
