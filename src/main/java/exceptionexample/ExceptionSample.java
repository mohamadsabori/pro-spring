package main.java.exceptionexample;

import java.io.FileInputStream;
import java.io.IOException;

public class ExceptionSample implements AutoCloseable {

    public static void main(String[] args) throws IOException {
        try (FileInputStream stream = new FileInputStream("temp.txt")) {

        }
        try (var resource = new ExceptionSample()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Im closing!");
    }
}
