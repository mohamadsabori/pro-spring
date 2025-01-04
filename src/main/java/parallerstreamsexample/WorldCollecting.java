package main.java.parallerstreamsexample;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorldCollecting {
    public static void main(String[] args) {
        var stream1 = Stream.of('w', 'o', 'l', 'f');
        System.out.println(stream1.parallel().reduce("", (s, ch) -> s + ch, ((s1, s2) -> s1 + s2)));
        // Map with order
        var stream2 = Stream.of('w', 'o', 'l', 'f', 'o');
        stream2.collect(
                Collectors.toMap(
                        s -> s, s -> 1, Integer::sum, ConcurrentSkipListMap::new)).forEach((k, v) -> System.out.println(k + " " + v));

        var stream3 = Stream.of('w', 'o', 'l', 'f', 'o');
        stream3.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll).forEach(System.out::println);

        var stream4 = Stream.of('w', 'o', 'l', 'f', 'o', 'l');
        stream4.collect(ConcurrentSkipListMap<Character, Integer>::new, (map, c) -> map.merge(c, 1, Integer::sum),
                ConcurrentSkipListMap::putAll
        ).forEach((k, v) -> System.out.println(k + " " + v));
    }
}
