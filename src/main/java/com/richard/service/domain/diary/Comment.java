package com.richard.service.domain.diary;

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
 * desc: 评论
 */
@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = "TEXT")
    private String content;

    private long resourceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JoinColumn(name = "to_comment_id")
    private long toCommentId;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

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
    private int status;

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
    @JoinTable(name="extra_comment_child_comment",
            joinColumns={@JoinColumn(name="commentId",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="childCommentId",referencedColumnName="id")}
    )
    private Set<Comment> childComment = new HashSet<>();

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
    @JoinTable(name="extra_comment_praise",
            joinColumns={@JoinColumn(name="commentId",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="praiseId",referencedColumnName="id")}
    )
    private Set<Praise> praises = new HashSet<>();


    public Comment(){}
    public Comment(String content, long resourceId, User user) {
        this.content = content;
        this.resourceId = resourceId;
        this.user = user;
    }

    public Set<Praise> getPraises() {
        return praises;
    }

    public void setPraises(Set<Praise> praises) {
        this.praises = praises;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
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

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(long toCommentId) {
        this.toCommentId = toCommentId;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Set<Comment> getChildComment() {
        return childComment;
    }

    public void setChildComment(Set<Comment> childComment) {
        this.childComment = childComment;
    }
}
