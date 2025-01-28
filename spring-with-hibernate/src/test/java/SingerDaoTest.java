import com.prospring.hibernate.HibernateConfig;
import com.prospring.hibernate.dao.SingerDao;
import com.prospring.hibernate.entities.Album;
import com.prospring.hibernate.entities.Singer;
import jakarta.annotation.PostConstruct;
import net.bytebuddy.asm.Advice;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

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
        singers.forEach(s -> LOGGER.info(s.toString()));
    }

    @Test
    @DisplayName("Should return singer by id")
    public void shouldReturnById() {
        var singer = singerDao.findById(2L);
        assertEquals("Ben", singer.getFirstName());
        LOGGER.info(singer.toString());
    }

    @Test
    @DisplayName("Should save the singer with associations")
    @Sql(statements = {"DELETE FROM ALBUM WHERE SINGER_ID=(SELECT ID FROM SINGER WHERE FIRST_NAME='BB')",
            "DELETE FROM SINGER_INSTRUMENT WHERE SINGER_ID = (SELECT ID FROM SINGER WHERE FIRST_NAME='BB')",
            "DELETE FROM SINGER WHERE FIRST_NAME='BB'"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testInsertSinger() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("Some kind of Blues");
        album.setReleaseDate(LocalDate.of(1961, 7, 8));
        singer.addAlbum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);
        singerDao.save(singer);

        assertNotNull(singer.getId());

        var singers = singerDao.findWithAlbum();
        assertEquals(4, singers.size());
        listSingersWithAssociations(singers);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"classpath:testcontainers/add-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = {"classpath:testcontainers/remove-nina.sql"},
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void testUpdate() {
        Singer singer = singerDao.findById(4L);
        assertNotNull(singer);
        assertEquals("Simone", singer.getLastName());

        Album album = singer.getAlbums().stream().filter(
                a -> a.getTitle().equals("I Put a Spell on You")
        ).findFirst().orElse(null);
        assertNotNull(album);

        singer.setFirstName("Eunice Kathleen");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();
        var nina = singerDao.save(singer);
        assertEquals(version + 1, nina.getVersion());
        listSingersWithAssociations(singerDao.findWithAlbum());
    }

    private static void listSingersWithAssociations(List<Singer> singers) {
        LOGGER.info("---------- Listing singers with instruments");
        singers.forEach(singer -> {
            LOGGER.info("Singer is: {}", singer);
            if (!singer.getInstruments().isEmpty()) {
                singer.getInstruments().forEach(s -> LOGGER.info("\tInstrument " + s.toString()));
            }
            if (!singer.getAlbums().isEmpty()) {
                singer.getAlbums().forEach(s -> LOGGER.info("\tAlbum " + s.toString()));
            }
        });
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
