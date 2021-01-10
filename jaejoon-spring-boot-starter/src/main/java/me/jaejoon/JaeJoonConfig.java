package me.jaejoon;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JaeJoonProperties.class)
public class JaeJoonConfig {

    @Bean
    @ConditionalOnMissingBean  // 이거 추가해주면 OK ! !
    JaeJoon jaeJoon(JaeJoonProperties jaeJoonProperties){
        JaeJoon jaeJoon = new JaeJoon();
        jaeJoon.setName(jaeJoonProperties.getName());
        jaeJoon.setAge(jaeJoonProperties.getAge());
        return jaeJoon;
    }
}
