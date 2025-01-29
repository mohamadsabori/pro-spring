import com.prospring.hibernate.dao.SingerDao;
import com.prospring.hibernate.entities.Album;
import com.prospring.hibernate.entities.Singer;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = {HibernateTestConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H2HibernateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2HibernateTest.class);

    @Autowired
    SingerDao singerDao;

    @Test
    @Order(1)
    @DisplayName("Should insert a singer with albums")
    public void testInsert() {
        var singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));

        var album = new Album();
        album.setTitle("My Kig of Blues");
        album.setReleaseDate(LocalDate.of(1961, 7, 18));
        singer.addAlbum(album);

        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(LocalDate.of(1962, 3, 20));
        singer.addAlbum(album);

        var created = singerDao.save(singer);
        assertNotNull(created.getId());
    }

    @Test
    @Order(2)
    @DisplayName("should return all singers")
    public void testFindAll() {
        var singers = singerDao.findAll();
        assertEquals(1, singers.size());
        singers.forEach(singer -> LOGGER.info(singer.toString()));
    }

    @Test
    @Order(3)
    @DisplayName("should update a singer")
    public void testUpdate() {
        var singer = singerDao.findById(1L);

        assertNotNull(singer);
        singer.setFirstName("Riley B. ");
        int version = singer.getVersion();
        var bb = singerDao.save(singer);
        assertEquals(version + 1, bb.getVersion());
    }

    @Test
    @Order(4)
    @DisplayName("should deleted a singer")
    public void testDelete() {
        var singer = singerDao.findById(1L);
        assertNotNull(singer);

        singerDao.delete(singer);

        assertEquals(0, singerDao.findAll().size());
    }
}
