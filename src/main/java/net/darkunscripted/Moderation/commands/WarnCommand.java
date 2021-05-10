package net.darkunscripted.Moderation.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.managers.EmbedManager;
import net.darkunscripted.Moderation.service.KickService;
import net.darkunscripted.Moderation.service.WarnService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class WarnCommand extends Command {

    private EventWaiter waiter;

    public WarnCommand(EventWaiter waiter){
        this.name = "warn";
        this.waiter = waiter;
        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
    }

    @Override
    protected void execute(CommandEvent e) {
        if(e.getArgs().length() < 2){
            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> You need to mention a name and give a reason!");
            e.getMessage().getChannel().sendMessage(embed.build()).queue();
        }else{
            String[] args = e.getArgs().split(" ");
            if(args[0].contains("@")) {
                e.getJDA().retrieveUserById(args[0].substring(3, args[0].length() - 1)).queue(user -> {
                    if(user != null){
                        try{
                            WarnService.getInstance().warn(user);
                            user.openPrivateChannel().queue((channel) -> {
                                String reason = "";
                                for(int i = 1; i< args.length; i++){
                                    reason += args[i] + " ";
                                }
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "You have been warned!");
                                embed.addField("**Moderator:**", e.getMessage().getAuthor().getName(), false);
                                embed.addField("**Guild:**", e.getGuild().getName(), false);
                                embed.addField("**Reason:**", reason, false);
                                channel.sendMessage(embed.build()).queue();
                            });
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully warned: <@" + user.getId() + ">");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }catch (Exception exception){
                            exception.printStackTrace();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }else{
                e.getJDA().retrieveUserById(args[0]).queue(user -> {
                    if(user != null){
                        try{
                            WarnService.getInstance().warn(user);
                            user.openPrivateChannel().queue((channel) -> {
                                String reason = "";
                                for(int i = 1; i< args.length; i++){
                                    reason += args[i] + " ";
                                }
                                EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "You have been warned!");
                                embed.addField("**Moderator:**", e.getMessage().getAuthor().getName(), false);
                                embed.addField("**Guild:**", e.getGuild().getName(), false);
                                embed.addField("**Reason:**", reason, false);
                                channel.sendMessage(embed.build()).queue();
                            });
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "Succesfully warned: <@" + user.getId() + ">");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }catch (Exception exception){
                            exception.printStackTrace();
                            EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> that user is not in this guild!");
                            e.getMessage().getChannel().sendMessage(embed.build()).queue();
                        }
                    }else{
                        EmbedBuilder embed = EmbedManager.getInstance().createEmbed("**Warn**", Color.BLUE, e.getMessage().getGuild().getName() + " ♢ " + e.getJDA().getSelfUser().getName(), "<@" + e.getMessage().getAuthor().getId() + "> Could not find that User!");
                        e.getMessage().getChannel().sendMessage(embed.build()).queue();
                    }
                });
            }
        }
    }

}
