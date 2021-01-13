package test.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Value("${test.projectName}")
    String projectName;

    @Value("${test.version}")
    int version;

    @Value("${test.projectNameAndVersion}")
    String projectNameAndVersion;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(projectName);
        System.out.println(version);
        System.out.println(projectNameAndVersion);
    }
}
