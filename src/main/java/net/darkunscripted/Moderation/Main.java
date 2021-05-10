package net.darkunscripted.Moderation;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.darkunscripted.Moderation.commands.*;
import net.darkunscripted.Moderation.domain.Settings;
import net.darkunscripted.Moderation.runnables.BanTrackerRunnable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Timer;

public class Main {

    public static Settings settings;

    public static void main(String[] args) throws LoginException {
        try {
            JDA jda = JDABuilder.createDefault("<token>").setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
            EventWaiter waiter = new EventWaiter();
            CommandClientBuilder builder = new CommandClientBuilder();
            builder.setOwnerId("347642857764421664");
            builder.setPrefix(".");
            builder.addCommand(new BanCommand(waiter));
            builder.addCommand(new UnbanCommand(waiter));
            builder.addCommand(new TempBanCommand(waiter));
            builder.addCommand(new MuteCommand(waiter));
            builder.addCommand(new UnmuteCommand(waiter));
            builder.addCommand(new TempMuteCommand(waiter));
            builder.addCommand(new KickCommand(waiter));
            builder.addCommand(new WarnCommand(waiter));

            CommandClient client = builder.build();

            jda.addEventListener(client);
            jda.addEventListener(waiter);

            Timer timer = new Timer();
            timer.schedule(new BanTrackerRunnable(), 0, 1000);
        }catch (LoginException exception){
            System.out.println("Something went wrong! could not login using the bot!");
        }
    }

}
