package insa.application.helpapp;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String handleError() {
        // Return the inline HTML content for the custom error page
        return "<html><head><title>Error</title></head><body><h1>Something went wrong!</h1><p>We are sorry, but the page you are looking for does not exist.</p><img src='https://i.ytimg.com/vi/lqvcsjvBFQA/sddefault.jpg' alt='Error Image'></body></html>";
    }
}