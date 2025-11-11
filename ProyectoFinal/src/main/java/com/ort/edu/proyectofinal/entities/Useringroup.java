package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;


@Entity
@Table(name = "useringroup", schema = "proyectofinal")
public class Useringroup {
    @EmbeddedId
    private UseringroupId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @MapsId("groupId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "GroupId", nullable = false)
    private Group group;

    public UseringroupId getId() {
        return id;
    }

    public void setId(UseringroupId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}