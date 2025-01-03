package main.java.customecomponentnaming;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Configuration
@ComponentScan(nameGenerator = SimpleBeanNameGenerator.class)
public class BeanNameConfig {
}

@Component
class SimpleBean {
}

class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNameConfig.class);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(s -> logger.info(s));
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}

