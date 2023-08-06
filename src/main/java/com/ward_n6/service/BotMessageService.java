package com.ward_n6.service;

import com.ward_n6.entity.BotMessaging;
import com.ward_n6.repository.BotMessagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotMessageService {// класс для обработки переписки
    // вероятно обработка отчётов тоже будет здесь, а составление самого отчёnа уже в классе отчёта

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
