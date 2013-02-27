package com.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "entered_exited_history" )
public class EnteredExitedHistory {
    private Long id;

    private Date entered;
    private Date exited;

    public EnteredExitedHistory() {
        // this form used by Hibernate
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
    @Column(name = "entered")
    public Date getEntered() {
        return entered;
    }

    public void setEntered(Date entered) {
        this.entered = entered;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exited")
    public Date getExited() {
        return exited;
    }

    public void setExited(Date exited) {
        this.exited = exited;
    }

    @Override
    public String toString() {
        return "EnteredExitedHistory{" +
                "id=" + id +
                ", entered=" + entered +
                ", exited=" + exited +
                '}';
    }
}