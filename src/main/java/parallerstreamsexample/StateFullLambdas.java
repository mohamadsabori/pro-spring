package main.java.parallerstreamsexample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StateFullLambdas {
    // Lambda is changing the state of 'data' which is not good practice and would cause of in ordered result
    List<Integer> addValues(IntStream stream) {
        var data = Collections.synchronizedList(new ArrayList<Integer>());
        stream.filter(e -> e % 2 == 0).forEach(data::add);
        return data;
    }

    List<Integer> betterAddValues(IntStream stream) {
        return stream.filter(e -> e % 2 == 0).boxed().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        new StateFullLambdas().addValues(IntStream.range(1, 11)).forEach(System.out::print);

        System.out.println();
        System.out.println("*** State-full lambda execution in parallel fashion ***");
        new StateFullLambdas().addValues(IntStream.range(1, 11).parallel()).forEach(System.out::print);

        System.out.println();
        System.out.println("*** State-less lambda ('used boxed') execution in parallel fashion ***");
        new StateFullLambdas().betterAddValues(IntStream.range(1, 11).parallel()).forEach(System.out::print);
    }
}
