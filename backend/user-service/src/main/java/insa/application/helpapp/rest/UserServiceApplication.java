package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
@RestController
public class UserServiceApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    // https://stackoverflow.com/users/774398/patrick
    public static String generateRandomBase64Token(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength - 32];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); // base64 encoding
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    // CORS Configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    // Post should be : /create_user?idRole=1&username=toto&password=1234
    // Response: created user
    @PostMapping("/create_user")
    public User createUser(@RequestParam int idRole, @RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setIdRole(idRole);
        user.setUsername(username);
        user.setPassword(password);

        return userRepository.save(user);
    }

    // Post should be : /get_user?id=1
    // Response: idRole (int), username (string)
    @GetMapping("/get_user")
    public ResponseEntity<?> getUser(@RequestParam int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID not known.");
        }
        User user = userOptional.get();
        Map<String, Object> response = new HashMap<>();

        // Reponse to client
        response.put("username", user.getUsername());
        response.put("idRole", user.getIdRole());
        return ResponseEntity.ok(response);
    }

    // Post should be : /connect?username=toto&password=1234
    // Response can vary: Error if username/password doesn't exist/match
    // Response if success: userId (int), expiresAt (time), token (varchar(128))
    @PostMapping("/connect")
    public ResponseEntity<?> connect(@RequestParam String username, @RequestParam String password) {
        List<User> users = userRepository.findByUsername(username);
        // Checks username
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username not known.");
        }
        User user = users.getFirst();
        // Checks password
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
        }
        // Checks if token already exists
        List<Connection> connections = connectionRepository.findByIdUser(user.getId());
        Map<String, Object> response = new HashMap<>();
        Connection connection = new Connection();
        if (connections.isEmpty()
                || (!connections.isEmpty() && (connections.getFirst().getExpiresAt().isBefore(LocalDateTime.now())))) {
            // Remove the old token
            if (!connections.isEmpty()) {
                connectionRepository.delete(connections.getFirst());
            }
            // Create new token if password & username is correct
            // Get User ID
            connection.setidUser(user.getId());
            // Generate token
            connection.setToken(generateRandomBase64Token(128));
            // Creation date and expiration date
            connection.setCreatedAt(LocalDateTime.now());
            connection.setExpiresAt(LocalDateTime.now().plusDays(1));
            // Save created Connection
            connectionRepository.save(connection);
        } else {
            connection = connections.getFirst();
        }

        // Reponse to client
        response.put("token", connection.getToken());
        response.put("userId", connection.getidUser());
        response.put("expiresAt", connection.getExpiresAt());
        return ResponseEntity.ok(response);
    }

}
