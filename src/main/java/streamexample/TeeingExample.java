package main.java.streamexample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

record StringGroup(String firstValue, String commaValue) {
}

public class TeeingExample {
    public static void main(String[] args) {
        //Simple teeing
        var stream = Stream.of("W", "O", "L", "F");
        StringGroup group = stream.collect(teeing(
                joining(" "),
                joining(","),
                StringGroup::new
        ));
        System.out.println(group);
        //Create map of each element length and list of Strings as key
        var ohMy = Stream.of("Lions", "Tigers", "Beers");
        var grouping = ohMy.collect(groupingBy(String::length));
        System.out.println(grouping);
        //Grouping by String length and Optional Character(find by ascending("Natural ordering") Comparing)
        var ohMy2 = Stream.of("Aions", "Tigers", "Beers");
        var mappingResult = ohMy2.collect(groupingBy(
                String::length,
                mapping(
                        s -> s.charAt(0),
                        minBy(Comparator.comparingInt(a -> a))
                )
        ));
        System.out.println(mappingResult);
        var doubleString = "HelooWwoworldd";
        TreeMap<Character, Integer> collect = doubleString.chars().
                mapToObj(c -> (char) c).
                collect(toMap(s -> s, s -> 1, Integer::sum, TreeMap::new));
        collect.forEach((a, b) -> System.out.println(a + " " + b));

        var naturalMapping = (Integer) Stream.of("Lions", "Tigers", "Beers").map(s -> s.charAt(0)).mapToInt(a -> a).sum();

    }
}
