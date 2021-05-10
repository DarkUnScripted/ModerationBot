package net.darkunscripted.Moderation.service;

import net.darkunscripted.Moderation.domain.Warn;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarnService {

    private static WarnService ws = new WarnService();
    private List<Warn> warns = new ArrayList<>();

    public static WarnService getInstance(){
        return ws;
    }

    public Warn warn(User user){
        Warn warn = new Warn(UUID.randomUUID(), user, System.currentTimeMillis());
        warns.add(warn);
        return warn;
    }

}
