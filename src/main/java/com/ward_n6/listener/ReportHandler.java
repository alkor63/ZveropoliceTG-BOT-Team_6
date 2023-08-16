package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.service.ReportService;

import java.util.function.Consumer;

public class ReportHandler implements EventHandler {
    private final OwnerReport ownerReport = new OwnerReport();

    private final ReportService reportService;
    private final TelegramBot bot;

    public ReportHandler(ReportService reportService, TelegramBot bot, Consumer<Update> actionOnNextMessage) {
        this.reportService = reportService;
        this.bot = bot;
    }

    private Consumer<Update> actionOnNextMessage; // переменная для определения действий над поступаемым сообщением
    boolean isId = false;
    boolean isHealth = false;
    boolean isFeed = false;

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        var text = update.message().text();
        switch (text) {
            case "/ID":
                bot.execute(new SendMessage(update.message().chat().id(), "Укажите ID Вашего питомца"));
                actionOnNextMessage = upd -> {
                    var idMessage = upd.message().text();

                    if (idMessage.matches("\\d+")) { // проверяем, что указано число
                        ownerReport.setId(Long.parseLong(idMessage));
                        bot.execute(new SendMessage(upd.message().chat().id(), "ID питомца записан."));
                    } else {
                        bot.execute(new SendMessage(upd.message().chat().id(), "ID питомца указан неверно."));
                    }
                };
                break;

            case "/health":
                bot.execute(new SendMessage(update.message().chat().id(), "Опишите кратко самочувствие питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setPetsHealth(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Записано!"));
                };
                break;

            case "/feed":
                bot.execute(new SendMessage(update.message().chat().id(), "Опишите рацион питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setNutrition(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Записано!"));
                };
                break;

            case "/save":
                reportService.save(ownerReport);
                bot.execute(new SendMessage(update.message().chat().id(), "Ваш отчёт загружен"));
                return true; // возвращаем true - это значит, что контекст завершен.
            default:
        }
        return false;
    }
}
