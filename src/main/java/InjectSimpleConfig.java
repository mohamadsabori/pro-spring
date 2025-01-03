package main.java;

import org.springframework.stereotype.Component;

@Component
public class InjectSimpleConfig {
    private final String name = "John Mayer";

    public String getName() {
        return name;
    }
}
