package main.java;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InjectedSimpleSpELDemo {
    @Value("#{injectSimpleConfig.name.toUpperCase()}")
    private String name;

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(InjectedSimpleSpELDemo.class, InjectSimpleConfig.class);
        ctx.refresh();

        var injected = ctx.getBean(InjectedSimpleSpELDemo.class);

        System.out.println(injected);
    }

    @Override
    public String toString() {
        return name;
    }
}
