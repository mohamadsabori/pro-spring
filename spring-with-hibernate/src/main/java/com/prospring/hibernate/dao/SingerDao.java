package com.prospring.hibernate.dao;

import com.prospring.hibernate.entities.Singer;

import java.util.List;
import java.util.Set;

public interface SingerDao {
    List<Singer> findAll();
    List<Singer> findWithAlbum();
    Singer findById(Long id);
    Singer save(Singer singer);
    void delete(Singer singer);
    Set<String> findAllNamesByProjection();
    Singer findAllDetails(String firstName, String lastName);
    String findFirstNameById(Long id);
}
