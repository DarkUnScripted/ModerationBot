package net.darkunscripted.Moderation.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.Main;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.BanService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class UnbanCommand extends Command {

    private EventWaiter waiter;

    public UnbanCommand(EventWaiter waiter){
        this.name = "unban";
        this.waiter = waiter;
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getArgs().length() == 0){
            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Unban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> You need to mention a name!");
            e.getMessage().getChannel().sendMessage(embed.build()).queue();
        }else{
            String[] args = e.getArgs().split(" ");
            if(args[0].contains("@")) {
                e.getJDA().retrieveUserById(args[0].substring(3, args[0].length() - 1)).queue(user -> {
                    if(user != null){
                        BanService.getInstance().unban(user);
                        e.getGuild().unban(user.getId()).queue();
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Unban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully unbanned: <@" + user.getId() + ">");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Unban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }else{
                e.getJDA().retrieveUserById(args[0]).queue(user -> {
                    if(user != null){
                        BanService.getInstance().unban(user);
                        e.getGuild().unban(user.getId()).queue();
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Unban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully unbanned: <@" + user.getId() + ">");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Unban**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }
        }
    }

}
