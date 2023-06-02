package pp.project.vmm.config.security.rest.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {


    private UUID id;
    private String username;
    private List<String> roles;
    private String accessToken;
    private String tokenType;

    public JwtResponse(String jwt, UUID id, String username, List<String> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.tokenType = "Bearer";
    }

}
