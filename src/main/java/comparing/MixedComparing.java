package main.java.comparing;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MixedComparing {
    public static void main(String[] args) {
        //This equal to reversed order
        Comparator<Integer> c1 = ((o1, o2) -> o2 - o1);
        Comparator<Integer> c2 = Comparator.naturalOrder();
        // equals to c1
        Comparator<Integer> c3 = Comparator.reverseOrder();

        var list = Arrays.asList(5, 4, 7, 2);
        Collections.sort(list, c3);
        Collections.reverse(list);
        Collections.reverse(list);
        System.out.println(Collections.binarySearch(list, 2));
    }
}
