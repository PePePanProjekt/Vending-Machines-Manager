package pp.project.vmm.config.security.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import pp.project.vmm.config.security.jwt.JwtUtils;
import pp.project.vmm.config.security.model.ERole;
import pp.project.vmm.config.security.model.Role;
import pp.project.vmm.config.security.model.User;
import pp.project.vmm.config.security.repository.RoleRepository;
import pp.project.vmm.config.security.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class TestConfiguration {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TestConfiguration(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Initialize roles and create a default user
    @Bean
    public void createUsers() {
        for(ERole roleName : ERole.values()) {
            if(roleRepository.existsRoleByName(roleName)) {
                continue;
            }
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
        if(userRepository.existsByUsername("user")) {
            return;
        }
        User user = new User();
        user.setUsername("user");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("pass"));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName(ERole.ROLE_OWNER)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
