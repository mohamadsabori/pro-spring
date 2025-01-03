package main.java.streamexample;

import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> oddNumbers = Stream.iterate(1,
                n -> n < 100,
                n -> n * 2);
        System.out.println(randoms.findFirst().orElseThrow());
        oddNumbers.forEach(System.out::println);

        DoubleStream randomDouble = DoubleStream.generate(Math::random);
        IntStream randomInts = RandomGenerator.getDefault().ints().limit(5);
        randomInts.forEach(System.out::println);
        DoubleStream iteratedDouble = DoubleStream.iterate(.5, d -> d / 2);
        randomDouble.limit(3).forEach(System.out::println);
        iteratedDouble.limit(3).forEach(System.out::println);

        IntStream closedStream = IntStream.rangeClosed(1, 5);
        closedStream.forEach(System.out::println);
    }
}
