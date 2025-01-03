package main.java;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringManipulation {
    public static void main(String[] args) {
        if (args.length > 0) {
            var characterList = args[0].chars().mapToObj(c -> (char) c)
                    .collect(Collectors.toMap(
                            s -> s,
                            s -> 1,
                            Integer::sum
                    ));
            System.out.println(characterList);
                    /*.collect(Collectors.toMap(
                            s -> s, c -> 1, (a, b) -> 2
                    ));*/
        }
        var ohMy = Stream.of("lions", "tigers", "horse");
        System.out.println(ohMy.collect(Collectors.groupingBy(
                String::length
        )));
        ohMy = Stream.of("lions", "tigers", "horse");
        System.out.println(ohMy.collect(Collectors.toMap(s -> s, String::length)));
        Optional<String> val = Optional.empty();
        System.out.println(val.orElseGet(() -> String.valueOf(Math.random())));
        System.out.println(val.orElseThrow(RuntimeException::new));
        ohMy.forEach((s) ->
                System.out.println(s)
        );
    }
}
