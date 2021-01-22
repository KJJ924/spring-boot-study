package test.demo.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.demo.MVC.config.ApiMessage;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("hello")

    public String helloName(){
        return sampleService.getName()+"hello";
    }


    @PostMapping("user")
    @ResponseBody
    public User  getUser(@RequestBody User user){
        return user;
    }


    @GetMapping("th")
    public String testPage(Model model){
        model.addAttribute("name","JaeJoon");
        model.addAttribute("age",26);
        return "wellcome";
    }

    @GetMapping("page/{id}")
    public String page(@PathVariable int id){
        if(id==1) {
            throw new NotFoundPage("해당하는 페이지가 존재하지않음", id);
        }
        return "wellcome";
    }



}
