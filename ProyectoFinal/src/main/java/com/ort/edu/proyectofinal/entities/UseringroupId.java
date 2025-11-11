package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UseringroupId implements Serializable {
    private static final long serialVersionUID = -6410307892858387159L;
    @Column(name = "UserId", nullable = false)
    private Integer userId;

    @Column(name = "GroupId", nullable = false)
    private Integer groupId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UseringroupId entity = (UseringroupId) o;
        return Objects.equals(this.groupId, entity.groupId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, userId);
    }

}