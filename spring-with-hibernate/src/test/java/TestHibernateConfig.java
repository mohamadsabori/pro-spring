import com.prospring.hibernate.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class TestHibernateConfig {
    @Test
    public void shouldCreateHibernateSessionFactory() {
        var ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
        var sessionFactory = ctx.getBean("sessionFactory");
        var transactionManager = ctx.getBean("transactionManager");
        assertNotNull(sessionFactory);
        assertNotNull(transactionManager);
    }
}
