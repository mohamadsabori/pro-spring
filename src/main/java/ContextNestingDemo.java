package main.java;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.micrometer.common.util.StringUtils.isBlank;

@Configuration
class ParentConfig {
    @Bean
    public TitleProvider parentProvider() {
        return TitleProvider.instance(null);
    }

    @Bean
    public TitleProvider childProvider() {
        return TitleProvider.instance("Daughters");
    }
}

@Configuration
class ChildConfig implements ApplicationContextAware {
    public ApplicationContext context;

    @Bean
    public TitleProvider childProvider() {
        return TitleProvider.instance("No such a thing");
    }

    @Bean
    public Song song1(@Value("#{parentProvider.title}") String title) {
        return new Song(title);
    }

    @Bean
    public Song song2(@Value("#{childConfig.context.parent.getBean(\"childProvider\").title}") String title) {
        return new Song(title);
    }

    @Bean
    public Song song3(@Value("#{childProvider.title}") String title) {
        return new Song(title);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

class Song {
    private String title = "";

    public Song(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

class TitleProvider {
    private String title = "Gravity";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static TitleProvider instance(String title) {
        var childProvider = new TitleProvider();
        if (!isBlank(title)) {
            childProvider.setTitle(title);
        }
        return childProvider;
    }
}

public class ContextNestingDemo {
    public static void main(String[] args) {
        var parentConfig = new AnnotationConfigApplicationContext();
        parentConfig.register(ParentConfig.class);
        parentConfig.refresh();

        var childConfig = new AnnotationConfigApplicationContext();
        childConfig.register(ChildConfig.class);
        childConfig.setParent(parentConfig);
        childConfig.refresh();

        Song song1 = (Song) childConfig.getBean("song1");
        Song song2 = (Song) childConfig.getBean("song2");
        Song song3 = (Song) childConfig.getBean("song3");

        System.out.println("from parent ctx: " + song1.getTitle());
        System.out.println("from child ctx: " + song2.getTitle());
        System.out.println("from child ctx: " + song3.getTitle());
    }
}
