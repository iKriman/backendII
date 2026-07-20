package cl.duoc.minimarket.auth;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService users, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        AppUser user = users.findByUsername(request.username())
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.encodedPassword()))
                .orElseThrow(() -> new SecurityException("Credenciales invalidas"));
        LoginResponse response = new LoginResponse(
                jwtService.createToken(user.username(), user.roles()),
                "Bearer",
                user.username(),
                user.roles());
        return ResponseEntity.ok(EntityModel.of(response,
                linkTo(methodOn(AuthController.class).login(request)).withSelfRel()));
    }
}
