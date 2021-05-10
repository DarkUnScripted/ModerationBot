package net.darkunscripted.Moderation.service;

import net.darkunscripted.Moderation.domain.Ban;
import net.darkunscripted.Moderation.domain.TempBan;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanService {

    private static BanService bs = new BanService();
    private List<Ban> bans = new ArrayList<>();
    private List<TempBan> tempBans = new ArrayList<>();
    private List<TempBan> activeBans = new ArrayList<>();

    public static BanService getInstance(){
        return bs;
    }

    public List<Ban> getPermBans(){
        return bans;
    }

    public List<TempBan> getTempBans(){
        return tempBans;
    }

    public List<TempBan> getActiveBans(){
        return activeBans;
    }

    public boolean removeActive(TempBan ban){
        return activeBans.remove(ban);
    }

    public Ban permBan(User user){
        Ban ban = new Ban(UUID.randomUUID(), user, System.currentTimeMillis());
        bans.add(ban);
        return ban;
    }

    public TempBan TempBan(User user, Long milliseconds, Guild guild){
        TempBan tempBan = new TempBan(UUID.randomUUID(), user, System.currentTimeMillis(), System.currentTimeMillis() + milliseconds, guild);
        tempBans.add(tempBan);
        activeBans.add(tempBan);
        return tempBan;
    }

    public boolean unban(User user){
        Ban ban = bans.stream().filter(banned -> banned.getUser().equals(user)).findFirst().orElse(null);
        TempBan tempBan = activeBans.stream().filter(tempBanned -> tempBanned.getUser().equals(user)).findFirst().orElse(null);
        if(ban != null || tempBan != null){
            if(tempBan != null){
                activeBans.remove(tempBan);
            }
            return true;
        }else{
            return false;
        }
    }

}
