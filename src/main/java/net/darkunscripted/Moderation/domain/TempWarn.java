package net.darkunscripted.Moderation.domain;

import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

public class TempWarn {

    private UUID id;
    private User user;
    private Long timeMillis;
    private Long timeExpired;

    public TempWarn(){

    }

    public TempWarn(UUID id, User user, Long timeMillis, Long timeExpired){
        this.id = id;
        this.user = user;
        this.timeMillis = timeMillis;
        this.timeExpired = timeExpired;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Long getTimeMillis() {
        return timeMillis;
    }

    public Long getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(Long timeExpired) {
        this.timeExpired = timeExpired;
    }

}
