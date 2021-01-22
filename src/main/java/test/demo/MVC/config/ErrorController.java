package test.demo.MVC.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import test.demo.MVC.NotFoundPage;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NotFoundPage.class)
    @ResponseBody
    public ResponseEntity<ApiMessage> notFoundException(NotFoundPage notFoundPage){
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setInputValue(notFoundPage.getValue());
        apiMessage.setMessage(notFoundPage.getMessage());
        apiMessage.setStatusCode("404");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiMessage);
    }

}
