package pp.project.vmm.config.security.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private UUID userId;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
