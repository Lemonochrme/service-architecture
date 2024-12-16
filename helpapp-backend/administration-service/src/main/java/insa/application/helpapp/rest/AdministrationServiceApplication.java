package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/admin/requests")
public class AdministrationServiceApplication {

    private final Map<Long, String> requestStatusDatabase = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(AdministrationServiceApplication.class, args);
    }

    // Validate a request
    @PutMapping("/{id}/validate")
    public String validateRequest(@PathVariable Long id) {
        requestStatusDatabase.put(id, "Validated");
        return "Request " + id + " validated.";
    }

    // Reject a request with justification
    @PutMapping("/{id}/reject")
    public String rejectRequest(@PathVariable Long id, @RequestParam String reason) {
        requestStatusDatabase.put(id, "Rejected: " + reason);
        return "Request " + id + " rejected for reason: " + reason;
    }

    // View request statuses
    @GetMapping
    public Map<Long, String> viewAllStatuses() {
        return requestStatusDatabase;
    }
}
