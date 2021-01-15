package test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Test test;

    @Autowired
    String hello;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("=================");
        logger.info(hello);
        logger.info("=================");

    }
}
