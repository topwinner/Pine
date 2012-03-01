package com.meamka.pine;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: amka
 * Date: 01.03.12
 * Time: 10:53
 */
public class Comment {

    private int id;
    private String body;
    private int likes_count;
    private Date created_at;
    private int page;
    private int page_count;
    private Player player;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikesCount() {
        return likes_count;
    }

    public void setLikesCount(int likes_count) {
        this.likes_count = likes_count;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return page_count;
    }

    public void setPageCount(int page_count) {
        this.page_count = page_count;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
