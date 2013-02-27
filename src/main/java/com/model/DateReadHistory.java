package com.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "date_read_history" )
public class DateReadHistory {
    private Long id;

    private Date dateRead;

    public DateReadHistory() {
    }

    public DateReadHistory(Date dateRead) {
        this.dateRead = dateRead;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_read")
    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    @Override
    public String toString() {
        return "DateReadHistory{" +
                "id=" + id +
                ", dateRead=" + dateRead +
                '}';
    }
}