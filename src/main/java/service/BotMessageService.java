package service;

import entity.BotMessaging;
import org.springframework.stereotype.Service;
import repository.BotMessagingRepository;

import java.util.List;

@Service
public class BotMessageService {
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
