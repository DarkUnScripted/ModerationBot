package net.darkunscripted.Moderation.runnables;

import net.darkunscripted.Moderation.domain.TempBan;
import net.darkunscripted.Moderation.domain.TempMute;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.BanService;
import net.darkunscripted.Moderation.service.MuteService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

public class BanTrackerRunnable extends TimerTask {

    @Override
    public void run() {
        List<TempBan> expiredBans = new ArrayList<>();
        for(TempBan ban : BanService.getInstance().getActiveBans()){
            if(ban.getTimeExpired() <= System.currentTimeMillis()){
                ban.getGuild().unban(ban.getUser()).queue();
                expiredBans.add(ban);
                System.out.println("Succesfully unbanned: " + ban.getUser().getName());
                System.out.println(System.currentTimeMillis());
            }
        }
        for(TempBan tempBan : expiredBans){
            BanService.getInstance().removeActive(tempBan);
        }
        List<TempMute> expiredMutes = new ArrayList<>();
        for(TempMute mute : MuteService.getInstance().getActiveMutes()){
            if(mute.getTimeExpired() <= System.currentTimeMillis()){
                Role role = mute.getGuild().getRoles().stream().filter(mutedRole -> mutedRole.getName().equalsIgnoreCase("muted")).findFirst().orElse(null);
                if(role != null) {
                    try {
                        mute.getGuild().retrieveMember(mute.getUser()).queue(member -> {
                            expiredMutes.add(mute);
                            mute.getGuild().removeRoleFromMember(member, role).queue();
                        });
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace();
                    }
                }else{
                    System.out.println("[ERROR] no mute role!");
                }
            }
        }
        for(TempMute tempMute : expiredMutes){
            MuteService.getInstance().removeActive(tempMute);
        }
    }

}
