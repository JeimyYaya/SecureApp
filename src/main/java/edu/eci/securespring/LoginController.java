package edu.eci.securespring;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // usuario de prueba
    private String storedUser = "admin";
    private String storedPasswordHash = encoder.encode("1234");

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        if (username.equals(storedUser) && encoder.matches(password, storedPasswordHash)) {
            return "Login exitoso";
        }

        return "Credenciales incorrectas";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Servidor seguro funcionando";
    }
}