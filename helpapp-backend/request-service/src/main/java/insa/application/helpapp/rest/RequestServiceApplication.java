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

@SpringBootApplication
@RestController
public class RequestServiceApplication {
    @Autowired
    private AdministrationService administrationService;
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

    @PostMapping("/post_message")
    public ResponseEntity<?> postMessage(@RequestParam int idUser,@RequestParam String token, @RequestParam String message) {
        if(!administrationService.checkToken(idUser, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User or token invalid.");
        };

        
        return ResponseEntity.ok("Message posted");
    }
}
