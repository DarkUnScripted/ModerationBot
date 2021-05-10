package net.darkunscripted.Moderation.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.BanService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class TempBanCommand extends Command {

    private EventWaiter waiter;

    public TempBanCommand(EventWaiter waiter){
        this.name = "tempban";
        this.waiter = waiter;
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getArgs().length() <= 1){
            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**TempBan**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> You need to mention a name and a length!");
            e.getMessage().getChannel().sendMessage(embed.build()).queue();
        }else{
            String[] args = e.getArgs().split(" ");
            if(args[0].contains("@")) {
                e.getJDA().retrieveUserById(args[0].substring(3, args[0].length() - 1)).queue(user -> {
                    if(user != null){
                        try {
                            Long time = Long.parseLong(args[1]) * 1000;
                            BanService.getInstance().TempBan(user, time, e.getGuild());
                            String reason = "";
                            if (args.length > 2) {
                                for (int i = 2; i < args.length; i++) {
                                    reason += args[i] + " ";
                                }
                            }
                            e.getGuild().ban(user, 7, reason).queue();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Ban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully TempBanned: <@" + user.getId() + "> for: " + Long.parseLong(args[1]) + " seconds");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }catch (NumberFormatException exception){
                            exception.printStackTrace();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Ban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }else{
                e.getJDA().retrieveUserById(args[0]).queue(user -> {
                    if(user != null){
                        try {
                            Long time = Long.parseLong(args[1]) * 1000;
                            BanService.getInstance().TempBan(user, time, e.getGuild());
                            String reason = "";
                            if (args.length > 2) {
                                for (int i = 2; i < args.length; i++) {
                                    reason += args[i] + " ";
                                }
                            }
                            e.getGuild().ban(user, 7, reason).queue();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**TempBan**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully TempBanned: <@" + user.getId() + "> for: " + Long.parseLong(args[1]) + " seconds");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }catch (NumberFormatException exception){
                            exception.printStackTrace();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**TempBan**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }
        }
    }

}
