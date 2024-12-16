package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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
    public String authenticate(@RequestParam String email, @RequestParam String password) {
        return userDatabase.values().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .map(user -> "Authentication successful for user ID: " + user.getId())
                .orElse("Authentication failed");
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

    // User entity
    static class User {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String role; // REQUESTER, VOLUNTEER, ADMIN

        // Getters and setters
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
