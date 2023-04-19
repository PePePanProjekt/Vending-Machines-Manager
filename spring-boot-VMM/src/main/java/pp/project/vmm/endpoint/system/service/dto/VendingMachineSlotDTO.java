package pp.project.vmm.endpoint.system.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendingMachineSlotDTO {

    private int slotNumber;

    private UUID itemId;

    private String itemName;

    private float itemPrice;

    private int itemAmount;
}
