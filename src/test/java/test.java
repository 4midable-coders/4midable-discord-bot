import com.fourMidableCoders.fourMidableDiscordBot.ConfigLoader;
import com.fourMidableCoders.fourMidableDiscordBot.service.GoogleService;
import com.google.api.services.calendar.model.Event;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class test {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        System.out.println(GoogleService.getEvents());
    }
}
