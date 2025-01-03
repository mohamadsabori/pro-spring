package main.java.customecomponentnaming;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.Locale;
import java.util.UUID;

public class SimpleBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        var beanName = definition.getBeanClassName()
                .substring(
                        definition.getBeanClassName().lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        var uid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        return beanName + "-" + uid;
    }
}
