package main.java;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
class Inpiration {
    String lyrics = "I can keep the door open, to let the light through";

    public Inpiration(@Value("For all my running, I can understand") String lyric) {
        this.lyrics = lyric;
    }

    public String getLyrics() {
        return this.lyrics;
    }

    public void setLyrics(String lyric) {
        this.lyrics = lyric;
    }
}

public class SingerFieldInjectionDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Inpiration.class);
        ctx.register(Singer.class);
        ctx.refresh();

        Singer singer = ctx.getBean(Singer.class);
        singer.sing();
    }
}
