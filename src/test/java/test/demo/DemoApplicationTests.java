package test.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "test.projectName=testCodeProject")
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    Environment environment;
    @Test
    void propertiesTest() {
        Assertions.assertThat(environment.getProperty("test.projectName")).isEqualTo("testCodeProject");
    }

}
