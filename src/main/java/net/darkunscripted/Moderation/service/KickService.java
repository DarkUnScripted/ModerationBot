package net.darkunscripted.Moderation.service;

import net.darkunscripted.Moderation.domain.Kick;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KickService {

    private static KickService ks = new KickService();
    private List<Kick> kicks = new ArrayList<>();

    public static KickService getInstance(){
        return ks;
    }

    public Kick kick(User user){
        Kick kick = new Kick(UUID.randomUUID(), user, System.currentTimeMillis());
        kicks.add(kick);
        return kick;
    }

}
