package com.prospring.hibernate.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDate;

@Entity
@Table(name = "ALBUM")
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private Singer singer;
    private String title;
    private LocalDate releaseDate;

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "RELEASE_DATE")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    public Singer getSinger() {
        return singer;
    }

    @Override
    public String toString() {
        return "Album{" +
                "singer=" + singer +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
