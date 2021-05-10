package net.darkunscripted.Moderation.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.BanService;
import net.darkunscripted.Moderation.service.KickService;
import net.darkunscripted.Moderation.service.MuteService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class KickCommand extends Command {

    private EventWaiter waiter;

    public KickCommand(EventWaiter waiter){
        this.name = "kick";
        this.waiter = waiter;
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getArgs().length() == 0){
            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> You need to mention a name!");
            e.getMessage().getChannel().sendMessage(embed.build()).queue();
        }else{
            String[] args = e.getArgs().split(" ");
            if(args[0].contains("@")) {
                System.out.println(args[0].substring(3, args[0].length() - 1));
                e.getJDA().retrieveUserById(args[0].substring(3, args[0].length() - 1)).queue(user -> {
                    if(user != null){
                        try{
                            e.getGuild().retrieveMember(user).queue(member -> {
                                KickService.getInstance().kick(user);
                                e.getGuild().kick(member).queue();
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully kicked: <@" + user.getId() + ">");
                                e.getMessage().getChannel().sendMessage(embed.build()).queue();
                            });
                        }catch (Exception exception){
                            exception.printStackTrace();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }else{
                e.getJDA().retrieveUserById(args[0]).queue(user -> {
                    if(user != null){
                        try{
                            e.getGuild().retrieveMember(user).queue(member -> {
                                KickService.getInstance().kick(user);
                                e.getGuild().kick(member).queue();
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully kicked: <@" + user.getId() + ">");
                                e.getMessage().getChannel().sendMessage(embed.build()).queue();
                            });
                        }catch (Exception exception){
                            exception.printStackTrace();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Kick**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }
        }
    }

}
