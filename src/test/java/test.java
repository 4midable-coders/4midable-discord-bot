import com.fourMidableCoders.fourMidableDiscordBot.ConfigLoader;

import java.util.Properties;

public class test {
    public static void main(String[] args) {
        Properties prop = ConfigLoader.loadProperties();
        String token = prop.getProperty("DISCORD_BOT_TOKEN");
        System.out.println(token);
    }
}
