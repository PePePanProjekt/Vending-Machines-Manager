package pp.project.vmm.config.security.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String jwt;
    private UUID id;
    private String username;
    private List<String> roles;
}
