package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "menuitempreparationtime", schema = "proyectofinal")
public class Menuitempreparationtime {
    @Id
    @Column(name = "MenuItemId", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MenuItemId", nullable = false)
    private Menuitem menuitem;

    @Column(name = "Time", nullable = false)
    private Integer time;

    @ColumnDefault("current_timestamp()")
    @Column(name = "CreatedDate", nullable = false)
    private Instant createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menuitem getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(Menuitem menuitem) {
        this.menuitem = menuitem;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}