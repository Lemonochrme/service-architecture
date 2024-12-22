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

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
@RestController
public class RequestServiceApplication {
    @Autowired
    private AdministrationService administrationService;
    @Autowired
    private RequestRepository requestRepository;

    public static void main(String[] args) {
        SpringApplication.run(RequestServiceApplication.class, args);
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

    // Post should be : /create_request?idUser=1&message=string&token=?
    // Response if success: the request
    @PostMapping("/create_request")
    public ResponseEntity<?> CreateRequest(@RequestParam int idUser, @RequestParam String token,
            @RequestParam String message) {
        if (!administrationService.checkToken(idUser, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User or token invalid.");
        }

        int idRole = administrationService.getRole(idUser).get();
        if (idRole == RoleEnum.VOLUNTEER.getValue()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Volunteers cannot post a request.");
        }

        Request request = new Request();
        request.setIdStatus(StatusEnum.WAITING.getValue());
        request.setIdUser(idUser);
        request.setCreatedAt(LocalDateTime.now());
        request.setMessage(message);
        return ResponseEntity.ok(requestRepository.save(request));
    }

    // Put should be : /change_status?idUser=1&idMessage=1&status=1&token=?
    // Response if success: the request with the changed status
    @PutMapping("/change_status")
    public ResponseEntity<?> changeStatus(@RequestParam int idUser, @RequestParam String token,
            @RequestParam int idMessage, @RequestParam int status) {
        if (!administrationService.checkToken(idUser, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User or token invalid.");
        }
        Optional<Request> requestOption = requestRepository.findById(idMessage);
        if (!requestOption.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No message found with the following ID.");
        }
        Request request = requestOption.get();

        int idRole = administrationService.getRole(idUser).get();
        if (status > StatusEnum.values().length) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The status of a request cannot exceed " + StatusEnum.values().length + ".");
        }
        // The admin can do anything
        else if (idRole == RoleEnum.ADMIN.getValue()) {
            request.setIdStatus(status);
            requestRepository.save(request);
            return ResponseEntity.ok(request);
        }
        // If the message is VALIDATED/SELECTED, the Volunteer can change its value
        else if (idRole == RoleEnum.VOLUNTEER.getValue()) {
            System.out.printf("Status: %d\tActual: %d%n", status, request.getIdStatus());
            if ((status == StatusEnum.VALIDATED.getValue() || status == StatusEnum.SELECTED.getValue())
                    && (request.getIdStatus() == StatusEnum.VALIDATED.getValue()
                            || request.getIdStatus() == StatusEnum.SELECTED.getValue())) {
                request.setIdStatus(status);
                requestRepository.save(request);
                return ResponseEntity.ok(request);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Volunteers can only change the status of a request to SELECTED/VALIDATED.");
            }
        }
        // The USER can only put the status to FINISHED after it is SELECTED
        else if (idRole == RoleEnum.USER.getValue()) {
            if ((status == StatusEnum.FINISHED.getValue())
                    && (request.getIdStatus() == StatusEnum.SELECTED.getValue())) {
                request.setIdStatus(status);
                requestRepository.save(request);
                return ResponseEntity.ok(request);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Users can only change the status of a request from SELECTED to FINISHED.");
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Only Admins can perform this action.");
    }


}
