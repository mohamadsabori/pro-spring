package main.java.comparing;

import java.util.*;

public class W {
}

class X extends W {
}

class Y extends X {
}

class Z<Y> {
    X x1 = new X();
    //X x2 = new Y();
    W w1 = new W();
    W w2 = new X();

    //W w3 = new Y();
    void addItem() {
        Queue<Integer> p = new LinkedList<>();
        p.add(10);
        p.add(12);
        p.remove(1);
        System.out.println(p);
    }

    void listAddItem() {
        List<Integer> p = new LinkedList<>();
        p.add(10);
        p.add(12);
        p.remove(1);
        System.out.println(p);
    }

    void varAddItem() {
        var p = new LinkedList<>();
        p.add(10);
        p.add(12);
        p.remove(1);
        System.out.println(p);
    }

    void mapCheck() {
        Map p = new HashMap();
        p.put(123, "456");
        p.put("abc", "def");

//        System.out.println(p.contains("123"));
    }

    void copyCollections() {
        var map = Map.of(1, 2, 3, 6);
        var list = List.copyOf(map.entrySet());
        List<Integer> one = List.of(8, 16, 2);
        var copy = List.copyOf(one);
        var copyOfCopy = List.copyOf(copy);
        var thirdCopy = List.copyOf(copyOfCopy);
//        list.replaceAll(x -> x * 2);
        one.replaceAll(x -> x * 2);
        thirdCopy.replaceAll(x -> x * 2);
        System.out.println(thirdCopy);
    }

    public static <T> T identity(T t) {
        return t;

    }

    void mergeExample() {
        var map = new HashMap<Integer, Integer>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, null);
        map.merge(1, 3, (a, b) -> a + b);
        map.merge(3, 3, (a, b) -> a + b);
        System.out.println(map);
    }

    public static void main(String[] args) {
//        new Z<>().listAddItem();
//        new Z<>().varAddItem();
//        new Z<>().copyCollections();
        new Z<>().mergeExample();
    }
}
