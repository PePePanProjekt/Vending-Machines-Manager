package pp.project.vmm.endpoint.system.service.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO used hold detailed vending machine information without Hibernate relations
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class VendingMachineDetailsDTO {

    private UUID id;

    @NonNull
    private String location;

    @NonNull
    private String name;

    @NonNull
    private Integer dispenserAmount;

    @NonNull
    private Integer dispenserDepth;
}
