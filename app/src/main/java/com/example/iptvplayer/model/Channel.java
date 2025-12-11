package com.example.iptvplayer.model;

public class Channel {
    private String id;
    private String name;
    private String url;
    private String logo;
    private String group;
    private boolean isFavorite;

    public Channel() {
        this.isFavorite = false;
    }

    public Channel(String id, String name, String url, String logo, String group) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.logo = logo;
        this.group = group;
        this.isFavorite = false;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}