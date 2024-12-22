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
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    @PostMapping("/post_request")
    public ResponseEntity<?> postMessage(@RequestParam int idUser,@RequestParam String token, @RequestParam String message) {
        if(!administrationService.checkToken(idUser, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User or token invalid.");
        };
        Optional<Integer> idRoleOption = administrationService.getRole(idUser);
        if(!idRoleOption.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User invalid.");
        };
        int idRole = idRoleOption.get();
        if(idRole == RoleEnum.VOLUNTEER.getValue()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Volunteers cannot post a request.");
        }

        Request request = new Request();
        // id_status = 1 means waiting. it is always set to 1 when created.
        request.setIdStatus(1);
        request.setIdUser(idUser);
        request.setCreatedAt(LocalDateTime.now());
        request.setMessage(message);
        return ResponseEntity.ok(requestRepository.save(request));
    }
}
