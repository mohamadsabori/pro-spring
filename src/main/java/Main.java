package main.java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(ConfigurableMessageProvider.class);
        ctx.refresh();
        var provider = ctx.getBean(MessageProvider.class);

        System.out.println(provider.getMessage());
    }
}