package pp.project.vmm.endpoint.finance.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZeroSaleMachine {
    UUID machineId;
    String machineName;
    String machineLocation;
}
