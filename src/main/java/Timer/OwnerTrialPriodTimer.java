package Timer;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Component;
import repository.BotMessagingRepository;

@Component
public class OwnerTrialPriodTimer {
    // класс для отслкживания испытательного срока
    private final BotMessagingRepository botMessagingRepository;
    private final TelegramBot telegramBot;


    public OwnerTrialPriodTimer(BotMessagingRepository botMessagingRepository, TelegramBot telegramBot) {
        this.botMessagingRepository = botMessagingRepository;
        this.telegramBot = telegramBot;
    }

    // продление испытательного срока
    // @Scheduled
    // удаление из БД / перенос в чёрный список после ИС
    //завершение испытательного срока
}
