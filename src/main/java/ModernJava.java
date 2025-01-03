package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModernJava {
    public static void main(String[] args) {
        useReduction();
        useCollectors();
//        useStream();
//        checkAnimalNamesBeginWithLetter();
    }

    private static void useStream() {
        Stream<Integer> numbers = Stream.iterate(1,
                n -> n < 100,
                n -> n + 2);
//        numbers.forEach(System.out::println);
        var list = new ArrayList<>();
        Stream.of(1, 2, 3).forEach(System.out::println);
        System.out.println(Stream.generate(() -> 100_000 * 1).findFirst().orElseThrow());
        System.out.println(Stream.generate(Math::random).findFirst().orElseThrow());
    }

    private static void checkAnimalNamesBeginWithLetter() {
        var list = List.of("monkey", "2", "chimp");
        Stream<String> infinite = Stream.generate(() -> "shimp");
        Predicate<String> predicate = x -> Character.isLetter(x.charAt(0));
        System.out.println(list.stream().anyMatch(predicate));
        System.out.println(list.stream().allMatch(predicate));
        System.out.println(list.stream().noneMatch(predicate));
        System.out.println(infinite.anyMatch(predicate));
    }

    private static void useReduction() {
        var numbers = Stream.of(1, 10, 5, 8);
        System.out.println(numbers.reduce(1, (a, b) -> a * b));
//        numbers.reduce((a, b) -> a * b).ifPresent(System.out::println);
        var stream = Stream.of("w", "o", "l", "f");
        stream.reduce(String::concat)
                .ifPresent(System.out::println);
    }

    //Or mutable reduction
    private static void useCollectors() {
        var words = Stream.of("w", "o", "l", "f");
        System.out.println(words.collect(StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append).toString());
    }
}
