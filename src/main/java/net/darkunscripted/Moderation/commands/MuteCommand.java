package net.darkunscripted.Moderation.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.BanService;
import net.darkunscripted.Moderation.service.MuteService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;

public class MuteCommand extends Command {

    private EventWaiter waiter;

    public MuteCommand(EventWaiter waiter){
        this.name = "mute";
        this.waiter = waiter;
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getArgs().length() == 0){
            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> You need to mention a name!");
            e.getMessage().getChannel().sendMessage(embed.build()).queue();
        }else{
            String[] args = e.getArgs().split(" ");
            if(args[0].contains("@")) {
                e.getJDA().retrieveUserById(args[0].substring(3, args[0].length() - 1)).queue(user -> {
                    if(user != null){
                        Role role = e.getGuild().getRoles().stream().filter(mutedRole -> mutedRole.getName().equalsIgnoreCase("muted")).findFirst().orElse(null);
                        if(role != null) {
                            try{
                                e.getGuild().retrieveMember(user).queue(member -> {
                                    MuteService.getInstance().permMute(user);
                                    e.getGuild().addRoleToMember(member, role).queue();
                                    EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully muted: <@" + user.getId() + ">");
                                    e.getMessage().getChannel().sendMessage(embed.build()).queue();
                                });
                            }catch (Exception exception){
                                exception.printStackTrace();
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                                e.getMessage().getChannel().sendMessage(embed.build()).queue();
                            }
                        }else{
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> make a role called 'Muted' to be able to mute");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }else{
                e.getJDA().retrieveUserById(args[0]).queue(user -> {
                    if(user != null){
                        Role role = e.getGuild().getRoles().stream().filter(mutedRole -> mutedRole.getName().equalsIgnoreCase("muted")).findFirst().orElse(null);
                        if(role != null) {
                            try{
                                e.getGuild().retrieveMember(user).queue(member -> {
                                    MuteService.getInstance().permMute(user);
                                    e.getGuild().addRoleToMember(member, role).queue();
                                    EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully muted: <@" + user.getId() + ">");
                                    e.getMessage().getChannel().sendMessage(embed.build()).queue();
                                });
                            }catch (Exception exception){
                                exception.printStackTrace();
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                                e.getMessage().getChannel().sendMessage(embed.build()).queue();
                            }
                        }else{
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> make a role called 'Muted' to be able to mute");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Mute**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }
        }
    }

}
