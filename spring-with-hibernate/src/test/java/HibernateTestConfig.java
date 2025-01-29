import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.prospring.hibernate"})
@EnableTransactionManagement
public class HibernateTestConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateTestConfig.class);

    @Bean
    public DataSource dataSource() {
        try {
            var dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2).setName("testdb").build();
        } catch (Exception ex) {
            LOGGER.error("Embedded DataSource bean cannot be created!", ex);
            return null;
        }
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
        hibernateProp.put(Environment.HBM2DDL_AUTO, "create-drop");
        hibernateProp.put(Environment.SHOW_SQL, true);
        hibernateProp.put(Environment.FORMAT_SQL, true);
        hibernateProp.put(Environment.MAX_FETCH_DEPTH, 3);
        hibernateProp.put(Environment.STATEMENT_BATCH_SIZE, 10);
        hibernateProp.put(Environment.STATEMENT_FETCH_SIZE, 50);
        return hibernateProp;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("com.prospring.hibernate.entities")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
