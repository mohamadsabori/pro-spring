package main.java.functionaltests;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

public class SampleLambdaHelper {
    public static void main(String[] args) {
        if (true) {
            checkImmutableCollectionsMadeByFactoryMethod();
            return;
        }
        if (true) {
            checkEffectiveFinalVariable();
            return;
        }
        FunctionSample sample = () -> System.out.println("Hello Lambda");
        sample.startMe();
        Function<Integer, Integer> y = x -> x * x;
        System.out.print("Function result");
        System.out.println(y.apply(2));
        BiFunction<Integer, Integer, Integer> x = (b, c) -> b * b;
        BinaryOperator<Integer> z = (a, b) -> a * a;
        System.out.print("Binary operator result");
        System.out.println(z.apply(2, 0));
    }

    private static void checkEffectiveFinalVariable() {
        for (var i = 0; i < 3; i++) {
            if (i % 2 != 0) {
                int j = i;
                Supplier<Integer> s = () -> j;
                System.out.println(s.get());
            }
        }
    }

    private static void checkImmutableCollectionsMadeByFactoryMethod() {
        String[] array = new String[]{"a", "b", "c"};
        var of = List.of(array);
        var asList = Arrays.asList(array);
        var copy = List.copyOf(asList);
        try {
            of.add("d");
        } catch (UnsupportedOperationException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            copy.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
        asList.add("d");
    }
}
