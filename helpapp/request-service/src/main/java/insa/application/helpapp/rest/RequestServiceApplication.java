package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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

    // Create a new help request
    @PostMapping
    public HelpRequest createRequest(@RequestBody HelpRequest request) {
        long id = idGenerator.getAndIncrement();
        request.setId(id);
        request.setStatus("Pending");
        requestDatabase.put(id, request);
        return request;
    }

    // Get all requests (with optional status filter)
    @GetMapping
    public List<HelpRequest> getAllRequests(@RequestParam(required = false) String status) {
        if (status == null) {
            return new ArrayList<>(requestDatabase.values());
        }
        List<HelpRequest> filteredRequests = new ArrayList<>();
        for (HelpRequest request : requestDatabase.values()) {
            if (request.getStatus().equalsIgnoreCase(status)) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

    // Get a specific request by ID
    @GetMapping("/{id}")
    public HelpRequest getRequest(@PathVariable Long id) {
        return Optional.ofNullable(requestDatabase.get(id))
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    // Update a request (e.g., status or details)
    @PutMapping("/{id}")
    public HelpRequest updateRequest(@PathVariable Long id, @RequestBody HelpRequest updatedRequest) {
        if (!requestDatabase.containsKey(id)) {
            throw new RuntimeException("Request not found");
        }
        HelpRequest existingRequest = requestDatabase.get(id);
        if (updatedRequest.getDetails() != null) {
            existingRequest.setDetails(updatedRequest.getDetails());
        }
        if (updatedRequest.getStatus() != null) {
            existingRequest.setStatus(updatedRequest.getStatus());
        }
        return existingRequest;
    }

    // Delete a request
    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable Long id) {
        if (requestDatabase.remove(id) == null) {
            throw new RuntimeException("Request not found");
        }
        return "Request deleted successfully";
    }

    // HelpRequest entity
    static class HelpRequest {
        private Long id;
        private String details;
        private String status; // e.g., Pending, Validated, Rejected, Completed

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
