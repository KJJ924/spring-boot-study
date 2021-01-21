package test.demo.MVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
