package test.demo.MVC.config;

public class ApiMessage {

    private String statusCode;

    private String message;

    private Object InputValue;

    public ApiMessage(){
    }

    public ApiMessage(String statusCode, String message, Object inputValue) {
        this.statusCode = statusCode;
        this.message = message;
        InputValue = inputValue;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getInputValue() {
        return InputValue;
    }

    public void setInputValue(Object inputValue) {
        InputValue = inputValue;
    }
}
