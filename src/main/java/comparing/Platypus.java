package main.java.comparing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public record Platypus(String name, int beakLength) {
    @Override
    public String toString() {
        return "Platypus{" +
                "name='" + name + '\'' +
                ", beakLength=" + beakLength +
                '}';
    }

    public static void main(String[] args) {
        Platypus p1 = new Platypus("Paula", 3);
        Platypus p2 = new Platypus("Peter", 5);
        Platypus p3 = new Platypus("Peter", 7);
        List<Platypus> list = List.of(p1, p2, p3);
        Collections.sort(list, Comparator.comparing(Platypus::beakLength));
        Collections.sort(list, Comparator.comparing(Platypus::beakLength).reversed());
        Collections.sort(list, Comparator.comparing(Platypus::name).thenComparing(Platypus::beakLength));
        Collections.sort(list, Comparator.comparing(Platypus::name).thenComparing(Comparator.comparing(Platypus::beakLength).reversed()));
        Collections.sort(list, Comparator.comparing(Platypus::name).thenComparingInt(Platypus::beakLength).reversed());


    }
}
