package com.meamka.pine;

import java.net.URL;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 9:26
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    
    private int id;
    private String name;
    private String username;
    private URL url;
    private URL avatar_url;
    private String location;
    private String twitter_screen_name;
    private int drafted_by_player_id;
    private int shots_count;
    private int draftees_count;
    private int followers_count;
    private int following_count;
    private int comments_count;
    private int comments_received_count;
    private int likes_count;
    private int likes_received_count;
    private int rebounds_count;
    private int rebounds_received_count;
    private Date created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(URL avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTwitterScreenName() {
        return twitter_screen_name;
    }

    public void setTwitter_screen_name(String twitter_screen_name) {
        this.twitter_screen_name = twitter_screen_name;
    }

    public int getDrafted_by_player_id() {
        return drafted_by_player_id;
    }

    public void setDrafted_by_player_id(int drafted_by_player_id) {
        this.drafted_by_player_id = drafted_by_player_id;
    }

    public int getShots_count() {
        return shots_count;
    }

    public void setShots_count(int shots_count) {
        this.shots_count = shots_count;
    }

    public int getDraftees_count() {
        return draftees_count;
    }

    public void setDraftees_count(int draftees_count) {
        this.draftees_count = draftees_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public void setComments_received_count(int comments_received_count) {
        this.comments_received_count = comments_received_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public void setLikes_received_count(int likes_received_count) {
        this.likes_received_count = likes_received_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public void setRebounds_received_count(int rebounds_received_count) {
        this.rebounds_received_count = rebounds_received_count;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
