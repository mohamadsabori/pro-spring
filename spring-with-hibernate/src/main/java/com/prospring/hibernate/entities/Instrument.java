package com.prospring.hibernate.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String instrumentId;
    private Set<Singer> singers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    public Set<Singer> getSingers() {
        return this.singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    @Id
    @Column(name = "INSTRUMENT_ID")
    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }
}
