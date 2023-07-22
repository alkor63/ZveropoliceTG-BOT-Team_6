package listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import entity.BotMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import service.BotMessageService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBotPetShelterUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);

    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;

    public TelegramBotPetShelterUpdatesListener(TelegramBot telegramBot, BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream() // получаем поток апдейтов из листа
                    .filter(update -> update.message() != null)// фильтруем по тем, что не нулл
                    .forEach(update -> { // итерируемся по ним
                        logger.info("Processing update: {}", update); // записываем лог апдейтов на уровне инфо
                        Message message = update.message(); // получаем сообщение из текущего обновления
                        Long chatId = message.chat().id(); // получаем идентификатор чата, к которому относится апдейт
                        String messageText = message.text(); // получаем текст сообщения

                        switch (messageText) {
                            case "/start" ->
                                    sendMessege(chatId, "Здравствуйте. Это чатбот приюта для животных. Какой приют Вас интересуте?");

                            case "/dogs" -> sendMessege(chatId, "Вы выбрали приют для собак.");
                            case "/cats" -> sendMessege(chatId, "Вы выбрали приют для кошек.");

                            default -> {
                                if (messageText != null) { // проверяем, не пустой ли текст
                                    saveMesseges(chatId, messageText);
                                }
                            }
                            }
                        });
        } catch (
                Exception e) {
            logger.error(e.getMessage()); // ловим ошибку
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // успешно завершаем метод, без падения
    }

    private void saveMesseges(long chatId, String messageText) {
        if (messageText.isEmpty()) { // обрабатываем нулловое значение из парсинга
            sendMessege(chatId, "Неверный формат соообщения");

        } else {
            BotMessaging botMessaging = new BotMessaging(); // создаём новое напоминание
            botMessaging.setChatId(chatId); // присваиваем созданному напоминанию значения из нашего апдейта
            botMessaging.setBotMessage(messageText);
            botMessageService.save(botMessaging); // сохранили наше сообщение в БД
        }
    }


    // ОТПРАВКА ОТВЕТА БОТА:
    private void sendMessege(Long chatId, String message) { // выносим отправку в отдельный метод
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage); // сохраняем в переменную sendMessage
        if (!sendResponse.isOk()) { // если отправка сообщения не удалась
            logger.error("Ошибка отправки сообщения: {}", sendResponse.description()); // сообщаем об ошибке

        }
    }
}
