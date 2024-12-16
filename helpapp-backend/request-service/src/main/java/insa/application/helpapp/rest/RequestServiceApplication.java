package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
@RequestMapping("/requests")
public class RequestServiceApplication {

    private final Map<Long, HelpRequest> requestDatabase = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public static void main(String[] args) {
        SpringApplication.run(RequestServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final RestTemplate restTemplate = new RestTemplate();

    // Create a new help request
    @PostMapping
    public HelpRequest createRequest(@RequestBody HelpRequest request) {
        if (request.getUserId() == null || request.getDetails() == null) {
            throw new RuntimeException("User ID and details are required");
        }
        // Validate user via UserService
        if (!isUserValid(request.getUserId())) {
            throw new RuntimeException("Invalid user ID");
        }
        long id = idGenerator.getAndIncrement();
        request.setId(id);
        request.setStatus("Pending");
        requestDatabase.put(id, request);
        return request;
    }

    // Get requests for a specific user
    @GetMapping("/user/{userId}")
    public List<HelpRequest> getRequestsByUser(@PathVariable Long userId) {
        List<HelpRequest> userRequests = new ArrayList<>();
        for (HelpRequest request : requestDatabase.values()) {
            if (request.getUserId().equals(userId)) {
                userRequests.add(request);
            }
        }
        return userRequests;
    }

    // Simulate user validation (integration with UserService)
    private boolean isUserValid(Long userId) {
        try {
            // Call UserService to check if the user exists
            String url = "http://localhost:8083/users/" + userId;
            restTemplate.getForObject(url, Object.class); // Throws exception if user doesn't exist
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // HelpRequest entity
    static class HelpRequest {
        private Long id;
        private Long userId;
        private String details;
        private String status; // Pending, Validated, Rejected, Completed

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
