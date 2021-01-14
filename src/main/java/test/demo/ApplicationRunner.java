package test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

//    @Value("${test.projectName}")
//    String projectName;
//
//    @Value("${test.version}")
//    int version;
//
//    @Value("${test.projectNameAndVersion}")
//    String projectNameAndVersion;

    @Autowired
    Test test;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(test.getProjectName());
        System.out.println(test.getVersion());
        System.out.println(test.getProjectNameAndVersion());
    }
}
