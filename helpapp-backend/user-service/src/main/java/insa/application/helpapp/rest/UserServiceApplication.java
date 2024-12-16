package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
@RequestMapping("/users")
public class UserServiceApplication {

    private final Map<Long, User> userDatabase = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

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

    // Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getPassword() == null || user.getRole() == null) {
            throw new RuntimeException("Password and role are required");
        }
        long id = idGenerator.getAndIncrement();
        user.setId(id);
        userDatabase.put(id, user);
        return user;
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return Optional.ofNullable(userDatabase.get(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Authenticate a user
    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return userDatabase.values().stream()
                .filter(user -> user.getEmail().equals(authRequest.getEmail()) && user.getPassword().equals(authRequest.getPassword()))
                .findFirst()
                .map(user -> new AuthResponse(user.getId(), "Authentication successful", true))
                .orElse(new AuthResponse(null, "Authentication failed", false));
    }

    // Update user details (excluding password)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = Optional.ofNullable(userDatabase.get(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        return existingUser;
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userDatabase.remove(id) == null) {
            throw new RuntimeException("User not found");
        }
        return "User deleted successfully";
    }

    // Data transfer objects
    static class AuthRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class AuthResponse {
        private Long userId;
        private String message;
        private boolean success;

        public AuthResponse(Long userId, String message, boolean success) {
            this.userId = userId;
            this.message = message;
            this.success = success;
        }

        public Long getUserId() {
            return userId;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    // User entity
    static class User {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String role; // REQUESTER, VOLUNTEER, ADMIN

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
