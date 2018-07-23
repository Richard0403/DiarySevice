package com.richard.service.security.demain.bean;

import javax.persistence.*;

/**
 * @author: Richard on
 * 2017/12/7
 * desc:
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
