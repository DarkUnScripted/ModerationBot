package net.darkunscripted.Moderation.domain;

import java.util.UUID;

public class Settings {

    private UUID id;
    private String prefix;
    private String token;

    public Settings(){

    }

    public Settings(UUID id, String prefix, String token){

    }

    public UUID getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getToken() {
        return token;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
