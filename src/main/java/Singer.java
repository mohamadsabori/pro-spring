package main.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("singer")
public class Singer {
    @Autowired
    private Inpiration inspiration;

    public void sing() {
        System.out.println("..." + inspiration.getLyrics());
    }
}
