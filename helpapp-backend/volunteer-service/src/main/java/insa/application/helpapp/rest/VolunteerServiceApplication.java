package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/volunteers")
public class VolunteerServiceApplication {

    private final Map<Long, String> volunteerActions = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(VolunteerServiceApplication.class, args);
    }

    // View available requests (simulating)
    @GetMapping("/requests")
    public List<String> viewAvailableRequests() {
        return List.of("Request 1: Grocery delivery", "Request 2: Medical help");
    }

    // Respond to a specific request
    @PostMapping("/{volunteerId}/help")
    public String respondToRequest(@PathVariable Long volunteerId, @RequestParam Long requestId) {
        volunteerActions.put(requestId, "Volunteer " + volunteerId + " responded.");
        return "Volunteer " + volunteerId + " responded to request " + requestId;
    }

    // View all volunteer actions
    @GetMapping("/actions")
    public Map<Long, String> viewActions() {
        return volunteerActions;
    }
}
