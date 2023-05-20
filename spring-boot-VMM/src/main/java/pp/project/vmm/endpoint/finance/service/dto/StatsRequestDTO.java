package pp.project.vmm.endpoint.finance.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsRequestDTO {

    private Date startDate;

    private Date endDate;

}
