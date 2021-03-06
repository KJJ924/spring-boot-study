package test.demo.MVC;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SampleController.class)
@AutoConfigureMockMvc
class MockSampleControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    SampleService sampleService;

    @Autowired
    ApplicationContext context;

    @Test
    void hello() throws Exception{
//        when(sampleService.getName()).thenReturn("mock Name");
        SampleService bean = context.getBean(SampleService.class);
        assertThat(bean).isNotNull();
        when(sampleService.getName()).thenReturn("mock Name");
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("mock Namehello"));
    }
}
