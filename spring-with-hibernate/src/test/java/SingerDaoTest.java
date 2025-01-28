import com.prospring.hibernate.HibernateConfig;
import com.prospring.hibernate.dao.SingerDao;
import jakarta.annotation.PostConstruct;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:testcontainers/drop-schema.sql", "classpath:testcontainers/create-schema.sql"})
@SpringJUnitConfig(classes = {SingerDaoTest.HibernateTestConfig.class})
public class SingerDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDaoTest.class);

    @Container
    static PostgreSQLContainer<?> postgresqlDB = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    SingerDao singerDao;

    @Test
    @DisplayName("Should return all singers")
    public void shouldReturnAll() {
        var singers = singerDao.findAll();
        assertEquals(3, singers.size());
        singers.forEach(System.out::println);
    }

    @Configuration
    @Import(HibernateConfig.class)
    public static class HibernateTestConfig {
        @Autowired
        Properties hibernateProperties;

        @PostConstruct
        public void initialize() {
            hibernateProperties.put(Environment.SHOW_SQL, true);
            hibernateProperties.put(Environment.FORMAT_SQL, true);
        }

    }
}
