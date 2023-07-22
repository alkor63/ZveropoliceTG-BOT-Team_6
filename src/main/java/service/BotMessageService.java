package service;

import entity.BotMessaging;
import org.springframework.stereotype.Service;
import repository.BotMessagingRepository;

import java.util.List;

@Service
public class BotMessageService {// класс для обработки переписки
    // вероятно обработка отчётов тоже будет здесь, а составление самого отчёа уже в классе отчёта

    private final BotMessagingRepository botMessagingRepository;

    public BotMessageService(BotMessagingRepository botMassagingRepository) {
        this.botMessagingRepository = botMassagingRepository;
    }

    public void save(BotMessaging botMessaging) {
        botMessagingRepository.save(botMessaging);
    }

    public List<BotMessaging> allCorrespondenceWithOwner() {
        return botMessagingRepository.findAll();
    }


}
