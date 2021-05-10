package net.darkunscripted.Moderation.domain;

import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

public class Mute {

    private UUID id;
    private User user;
    private Long timeMillis;

    public Mute(){

    }

    public Mute(UUID id, User user, Long timeMillis){
        this.id = id;
        this.user = user;
        this.timeMillis = timeMillis;
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
}
