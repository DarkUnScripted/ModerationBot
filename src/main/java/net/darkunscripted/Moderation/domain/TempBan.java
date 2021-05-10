package net.darkunscripted.Moderation.domain;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.UUID;

public class TempBan {

    private UUID id;
    private User user;
    private Long timeMillis;
    private Long timeExpired;
    private Guild guild;

    public TempBan(){

    }

    public TempBan(UUID id, User user, Long timeMillis, Long timeExpired, Guild guild){
        this.id = id;
        this.user = user;
        this.timeMillis = timeMillis;
        this.timeExpired = timeExpired;
        this.guild = guild;
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

    public Guild getGuild() {
        return guild;
    }

    public void setTimeExpired(Long timeExpired) {
        this.timeExpired = timeExpired;
    }

}
