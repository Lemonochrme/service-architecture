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

import java.util.Optional;
import java.util.List;

@SpringBootApplication
@RestController
public class FeedbackServiceApplication {
    @Autowired
    private AdministrationService administrationService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    public static void main(String[] args) {
        SpringApplication.run(FeedbackServiceApplication.class, args);
    }

    // CORS Configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

    @PostMapping("/create_feedback")
    public ResponseEntity<?> CreateFeedback(@RequestParam int idUser, @RequestParam String token,
            @RequestParam int idRequest, @RequestParam String message) {
        if (!administrationService.checkToken(idUser, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User or token invalid.");
        }
        Optional<Request> requestOption = requestRepository.findById(idRequest);
        if (!requestOption.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No request found with the following ID.");
        }
        Request request = requestOption.get();
        int idRole = administrationService.getRole(idUser).get();
        // Checks if the user id corresponds to a 'user'. if admin/volunteer can send feedback either way
        if((idRole == RoleEnum.USER.getValue()) && (request.getIdUser() != idUser))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only the author can respond to its own request.");
        }

        Feedback feedback = new Feedback();
        feedback.setIdRequest(idRequest);
        feedback.setIdUser(idUser);
        feedback.setMessage(message);
        return ResponseEntity.ok(feedbackRepository.save(feedback));
    }

    @GetMapping("/get_feedback")
    public ResponseEntity<?> GetFeedback(@RequestParam int idRequest)
    {
        List<Feedback> feedbacks = feedbackRepository.findByIdRequest(idRequest);
        if(feedbacks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No feedback exists for the following request.");
        }
        return ResponseEntity.ok(feedbacks);
    }
}
