package net.darkunscripted.Moderation.domain;

import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

public class Warn {

    private UUID id;
    private User user;
    private Long timeMillis;

    public Warn(){

    }

    public Warn(UUID id, User user, Long timeMillis){
        this.id = id;
        this.user = user;
        this.timeMillis = timeMillis;
    }

}
