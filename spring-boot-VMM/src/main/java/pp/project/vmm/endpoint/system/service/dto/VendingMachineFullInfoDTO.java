package pp.project.vmm.endpoint.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendingMachineFullInfoDTO {

    private VendingMachineDetailsDTO details;

    private List<VendingMachineSlotDTO> slots;
}
