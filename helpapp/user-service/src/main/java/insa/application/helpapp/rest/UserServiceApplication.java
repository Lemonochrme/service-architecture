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

    // Update a user by ID
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (!userDatabase.containsKey(id)) {
            throw new RuntimeException("User not found");
        }
        updatedUser.setId(id);
        userDatabase.put(id, updatedUser);
        return updatedUser;
    }

    // Delete a user by ID
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
        private String role;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
