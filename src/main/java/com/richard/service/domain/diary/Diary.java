package com.richard.service.domain.diary;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * desc: 日记
 */
@Entity
@Table
public class Diary {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String picture;

    @Column(name="updateTime")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;


    @Where(clause = "status != -1")
    @JoinColumn(columnDefinition = "int COMMENT '-1.删除状态，0.正常状态，1.热门'")
    private int status = 0;

    @JoinColumn(columnDefinition = "int COMMENT '0.保密，1.公开，2.对Ta公开'")
    private int publicStatus = 0;



    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private DiaryTag diaryTag;

    private int readNum = 0;

    private int shareNum = 0;

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
    @JsonBackReference
    @JoinTable(name="extra_diary_praise",
            joinColumns={@JoinColumn(name="diaryId",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="praiseId",referencedColumnName="id")}
    )
    private Set<Praise> praises = new HashSet<>();

    public Set<Praise> getPraises() {
        return praises;
    }

    public void setPraises(Set<Praise> praises) {
        this.praises = praises;
    }

    public int getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(int publicStatus) {
        this.publicStatus = publicStatus;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public DiaryTag getDiaryTag() {
        return diaryTag;
    }

    public void setDiaryTag(DiaryTag diaryTag) {
        this.diaryTag = diaryTag;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Diary() {
    }

    public Diary(String title, String content, User user, int publicStatus, String picture, DiaryTag diaryTag) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.publicStatus = publicStatus;
        this.picture = picture;
        this.diaryTag = diaryTag;
    }
}
