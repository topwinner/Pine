package com.meamka.pine;

import java.net.URL;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */
public class Shot {

    private int id;
    private String title;
    private URL url;
    private URL short_url;
    private URL image_url;
    private URL image_teaser_url;
    private int width;
    private int height;
    private int views_count;
    private int likes_count;
    private int comments_count;
    private int rebounds_count;
    private int rebound_source_id;
    private Date created_at;
    
    private Player player;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getShortUrl() {
        return short_url;
    }

    public void setShortUrl(URL short_url) {
        this.short_url = short_url;
    }

    public URL getImageUrl() {
        return image_url;
    }

    public void setImageUrl(URL image_url) {
        this.image_url = image_url;
    }

    public URL getImageTeaserUrl() {
        return image_teaser_url;
    }

    public void setImageTeaserUrl(URL image_teaser_url) {
        this.image_teaser_url = image_teaser_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getViewsCount() {
        return views_count;
    }

    public void setViewsCount(int views_count) {
        this.views_count = views_count;
    }

    public int getLikesCount() {
        return likes_count;
    }

    public void setLikesCount(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getCommentsCount() {
        return comments_count;
    }

    public void setCommentsCount(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public int getRebound_source_id() {
        return rebound_source_id;
    }

    public void setRebound_source_id(int rebound_source_id) {
        this.rebound_source_id = rebound_source_id;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
