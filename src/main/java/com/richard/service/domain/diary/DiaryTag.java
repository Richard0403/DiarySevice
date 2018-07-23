package com.richard.service.domain.diary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richard.service.domain.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * by Richard on 2017/8/25
 * desc: 标签
 */
@Entity
@Table
public class DiaryTag {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JoinColumn(nullable = false)
    private String name;
    @JoinColumn(nullable = false)
    private String description;
    @JoinColumn(nullable = false)
    private String picture;

    @Where(clause = "status != -1")
    @JoinColumn(columnDefinition = "int COMMENT '-1.删除状态，0.正常状态，1.热门'")
    private int status;

    @Column(name="updateTime")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @JsonBackReference
    @OneToMany(cascade={CascadeType.MERGE},orphanRemoval=true, mappedBy = "diaryTag")
    private Set<Diary> diarySet = new HashSet<>();


    public DiaryTag(){}
    public DiaryTag(User user, String name, String description, String picture) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Diary> getDiarySet() {
        return diarySet;
    }

    public void setDiarySet(Set<Diary> diarySet) {
        this.diarySet = diarySet;
    }

    @Override
    public boolean equals(Object obj) {
        if(getId() == (((DiaryTag)obj).getId())){
            return true;
        }else{
            return false;
        }
    }
}
