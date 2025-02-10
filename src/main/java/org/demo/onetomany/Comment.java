package org.demo.onetomany;

import jakarta.persistence.*;

/**
 * @author Auodumbar
 */

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int commentId;

    private String comment;

    @ManyToOne  //we don't specify this then we need enable join column annotation in post class,
    //also called as one directional relationship where Post entity knows Comment but Comment entity does not know about Posts(Post will be owner)
    private Post post;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", post=" + post +
                '}';
    }
}
