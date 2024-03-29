package pp.project.vmm.config.security.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import pp.project.vmm.config.security.rest.response.UserInfo;
import pp.project.vmm.config.security.service.UserDetailsImpl;

import java.util.*;
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

    @GetMapping("/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getUserInfo(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User of given id does not exist"));
        return ResponseEntity.ok(new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        ));
    }

    @GetMapping("/self")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getSelfInfo() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        ));
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
                roles
        ));
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already in use"));
        }
        User user = new User();
        userRepository.save(generateUser(user, registerRequest));
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }

    @PutMapping("/users/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId, @RequestBody RegisterRequest registerRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User of given id does nto exist"));
        userRepository.save(generateUser(user, registerRequest));
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/users/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deactivateUser(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user of given id does not exist"));
        user.setEnabled(false);
        userRepository.save(user);
        return ResponseEntity.ok(new UserInfo(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        ));
    }

    private User generateUser(User user, RegisterRequest registerRequest) {
        user.setUsername(registerRequest.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
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
        return user;
    }

}
