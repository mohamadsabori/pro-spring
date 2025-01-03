package main.java.optional;

import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        var opt = Optional.empty();
        System.out.println(opt.orElse(Double.NaN));
        System.out.println(opt.orElseThrow(() -> new IllegalStateException()));
    }
}
