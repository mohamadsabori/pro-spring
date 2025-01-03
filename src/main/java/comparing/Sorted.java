package main.java.comparing;

import java.util.Comparator;
import java.util.TreeSet;

public record Sorted(int num, String text)
        implements Comparable<Sorted>, Comparator<Sorted> {

    @Override
    public String toString() {
        return "" + num;
    }

    @Override
    public int compareTo(Sorted s) {
        return text.compareTo(s.text);
    }

    @Override
    public int compare(Sorted o1, Sorted o2) {
        return o1.num - o2.num;
    }

    public static void main(String[] args) {
        var num1 = new Sorted(88, "a");
        var num2 = new Sorted(55, "b");
        var t1 = new TreeSet<Sorted>();
        t1.add(num1);
        t1.add(num2);
        var t2 = new TreeSet<Sorted>(num1);
        t2.add(num1);
        t2.add(num2);
        System.out.println(t1 + " " + t2);
    }
}
