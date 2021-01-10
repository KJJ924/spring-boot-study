package test.demo;

import me.jaejoon.JaeJoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    JaeJoon jaeJoon;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(jaeJoon.getName());
        System.out.println(jaeJoon.getAge());
    }
}
