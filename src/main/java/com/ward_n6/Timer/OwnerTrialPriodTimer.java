package com.ward_n6.Timer;
import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.repository.BotMessagingRepository;
import org.springframework.stereotype.Component;

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

//*********************************************** ПРИМЕР ДЛЯ ТАЙМЕРА ******************************
//    private final NotificationTaskRepository notificationTaskRepository;
//    private final TelegramBot telegramBot;
//
//    public NotificationTaskTimer(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
//        this.notificationTaskRepository = notificationTaskRepository;
//        this.telegramBot = telegramBot;
//    }
//
//    //УДАЛЕНИЕ ИЗ БД ПО ИСТЕЧЕНИИ ЗАДАННОГО ВРЕМЕНИ:
//    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.DAYS)
//    // fixedDelay - задержка между концом и началом, fixeRate - задержка между началами
//    public void deleteTask() {
//        notificationTaskRepository.findAllByNotificationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
//                .forEach(notificationTask -> {
//                    telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getMessage()));
//                    notificationTaskRepository.delete(notificationTask);
//                });
//    }
//
//    // НАПОМИНАНИЕ О ЗАДАЧЕ:
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
//    // fixedDelay - задержка между концом и началом, fixeRate - задержка между началами
//    public void sendTask() {
//        notificationTaskRepository.findAllByNotificationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
//                .forEach(notificationTask -> {
//                    telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getMessage()));
//                });
//    }
//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
//    public List<NotificationTask> findAllByTime(){
//        return notificationTaskRepository.findAllByNotificationTime(LocalDateTime.now());}
}

