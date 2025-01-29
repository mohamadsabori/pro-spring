package com.prospring.hibernate.dao;

import com.prospring.hibernate.entities.Album;
import com.prospring.hibernate.entities.Instrument;
import com.prospring.hibernate.entities.Singer;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingerDaoImpl.class);
    private final SessionFactory sessionFactory;
    private final String ALL_SELECT = """
            SELECT DISTINCT s.FIRST_NAME,s.LAST_NAME, a.TITLE, a.RELEASE_DATE,
            i.INSTRUMENT_ID
            FROM Singer s
            INNER JOIN ALBUM a on s.id=a.singer_id
            INNER JOIN SINGER_INSTRUMENT si on si.SINGER_ID=s.ID
            INNER JOIN INSTRUMENT i on si.INSTRUMENT_ID=i.INSTRUMENT_ID
            WHERE s.FIRST_NAME = :firstName and s.LAST_NAME = :lastName
            """;

    public SingerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Singer s")
                .list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Singer> findWithAlbum() {
        return sessionFactory.getCurrentSession().getNamedQuery("Singer.findAllWithAlbum").list();
    }

    @Transactional(readOnly = true)
    @Override
    public Singer findById(Long id) {
        return (Singer) sessionFactory.getCurrentSession()
                .getNamedQuery("Singer.findById")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Singer save(Singer singer) {
        sessionFactory.getCurrentSession().saveOrUpdate(singer);
        LOGGER.info("Singer saved with id {}", singer.getId());
        return singer;
    }

    @Override
    public void delete(Singer singer) {
        sessionFactory.getCurrentSession().delete(singer);
        LOGGER.info("Singer with id {} deleted!", singer.getId());
    }

    @Override
    public Singer findAllDetails(String firstName, String lastName) {
        List<Object[]> results = sessionFactory.getCurrentSession()
                .createNativeQuery(ALL_SELECT)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .list();
        var singer = new Singer();

        for (Object[] item : results) {
            if (singer.getFirstName() == null && singer.getLastName() == null) {
                singer.setFirstName((String) item[0]);
                singer.setLastName((String) item[1]);
            }
            var album = new Album();
            album.setTitle((String) item[2]);
            album.setReleaseDate(((Date) item[3]).toLocalDate());
            singer.addAlbum(album);

            var instrument = new Instrument();
            instrument.setInstrumentId((String) item[4]);
            singer.addInstrument(instrument);
        }
        return singer;
    }
}
