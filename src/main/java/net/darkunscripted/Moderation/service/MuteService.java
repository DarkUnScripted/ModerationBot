package net.darkunscripted.Moderation.service;

import net.darkunscripted.Moderation.domain.Ban;
import net.darkunscripted.Moderation.domain.Mute;
import net.darkunscripted.Moderation.domain.TempBan;
import net.darkunscripted.Moderation.domain.TempMute;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MuteService {

    private static MuteService ms = new MuteService();
    private List<Mute> mutes = new ArrayList<>();
    private List<TempMute> tempMutes = new ArrayList<>();
    private List<TempMute> activeMutes = new ArrayList<>();

    public static MuteService getInstance(){
        return ms;
    }

    public List<Mute> getPermMutes(){
        return mutes;
    }

    public List<TempMute> getTempMutes(){
        return tempMutes;
    }

    public List<TempMute> getActiveMutes(){
        return activeMutes;
    }

    public boolean removeActive(TempMute mute){
        return activeMutes.remove(mute);
    }

    public Mute permMute(User user){
        Mute mute = new Mute(UUID.randomUUID(), user, System.currentTimeMillis());
        mutes.add(mute);
        return mute;
    }

    public TempMute TempMute(User user, Long milliseconds, Guild guild){
        TempMute tempMute = new TempMute(UUID.randomUUID(), user, System.currentTimeMillis(), System.currentTimeMillis() + milliseconds, guild);
        tempMutes.add(tempMute);
        activeMutes.add(tempMute);
        return tempMute;
    }

    public boolean unMute(User user){
        Mute mute = mutes.stream().filter(muted -> muted.getUser().equals(user)).findFirst().orElse(null);
        TempMute tempMute = activeMutes.stream().filter(tempMutes -> tempMutes.getUser().equals(user)).findFirst().orElse(null);
        if(mute != null || tempMute != null){
            if(tempMute != null){
                activeMutes.remove(tempMute);
            }
            return true;
        }else{
            return false;
        }
    }

}
