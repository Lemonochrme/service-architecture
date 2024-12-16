package insa.application.helpapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
@RequestMapping("/feedbacks")
public class FeedbackServiceApplication {

    private final Map<Long, Feedback> feedbackDatabase = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public static void main(String[] args) {
        SpringApplication.run(FeedbackServiceApplication.class, args);
    }

    // Add feedback
    @PostMapping
    public Feedback addFeedback(@RequestBody Feedback feedback) {
        long id = idGenerator.getAndIncrement();
        feedback.setId(id);
        feedbackDatabase.put(id, feedback);
        return feedback;
    }

    // Get feedback by request ID
    @GetMapping("/request/{requestId}")
    public List<Feedback> getFeedbackByRequest(@PathVariable Long requestId) {
        List<Feedback> feedbackList = new ArrayList<>();
        for (Feedback feedback : feedbackDatabase.values()) {
            if (feedback.getRequestId().equals(requestId)) {
                feedbackList.add(feedback);
            }
        }
        return feedbackList;
    }

    // Feedback entity
    static class Feedback {
        private Long id;
        private Long requestId;
        private String comment;
        private Integer rating; // 1 to 5

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getRequestId() {
            return requestId;
        }

        public void setRequestId(Long requestId) {
            this.requestId = requestId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }
    }
}
