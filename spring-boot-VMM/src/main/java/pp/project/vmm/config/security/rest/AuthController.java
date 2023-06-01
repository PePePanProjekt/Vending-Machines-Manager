package pp.project.vmm.config.security.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pp.project.vmm.config.security.jwt.JwtUtils;
import pp.project.vmm.config.security.model.ERole;
import pp.project.vmm.config.security.model.Role;
import pp.project.vmm.config.security.model.User;
import pp.project.vmm.config.security.repository.RoleRepository;
import pp.project.vmm.config.security.repository.UserRepository;
import pp.project.vmm.config.security.rest.request.LoginRequest;
import pp.project.vmm.config.security.rest.request.RegisterRequest;
import pp.project.vmm.config.security.rest.response.JwtResponse;
import pp.project.vmm.config.security.rest.response.MessageResponse;
import pp.project.vmm.config.security.service.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already in use"));
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        List<String> stringRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if(stringRoles == null || stringRoles.isEmpty()) {
            Role role = roleRepository.findRoleByName(ERole.ROLE_MAINTENANCE)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roles.add(role);
        }
        else {
            for(String stringRole : stringRoles) {
                switch (stringRole) {
                    case "admin", "ADMIN" -> {
                        Role adminRole = roleRepository.findRoleByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(adminRole);
                    }
                    case "owner", "OWNER" -> {
                        Role ownerRole = roleRepository.findRoleByName(ERole.ROLE_OWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(ownerRole);
                    }
                    default -> {
                        Role maintenanceRole = roleRepository.findRoleByName(ERole.ROLE_MAINTENANCE)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(maintenanceRole);
                    }
                }
            }
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }

}
