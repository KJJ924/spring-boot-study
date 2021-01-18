package test.demo.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("hello")
    public String helloName(){
        return sampleService.getName()+"hello";
    }


    @PostMapping("user")
    public User  getUser(@RequestBody User user){
        return user;
    }

}
