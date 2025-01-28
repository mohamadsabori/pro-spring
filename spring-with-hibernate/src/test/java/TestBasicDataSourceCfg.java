import com.prospring.hibernate.BasicDataSourceCfg;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class TestBasicDataSourceCfg {
    @Test
    public void testMySqlDataSourceBeanCreated() {
        var application = new AnnotationConfigApplicationContext(BasicDataSourceCfg.class);
        var dataSourceBean = application.getBean("dataSource");
        assertNotNull(dataSourceBean);
    }
}
