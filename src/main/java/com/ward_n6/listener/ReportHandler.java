package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.service.ReportService;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class ReportHandler implements EventHandler {
    public ReportHandler(ReportService reportService, TelegramBot bot) {

        this.reportService = reportService;
        this.bot = bot;
    }

    private OwnerReport ownerReport = new OwnerReport();

    private final ReportService reportService;
    private final TelegramBot bot;

    private Consumer<Update> actionOnNextMessage; // переменная для определения действий над поступаемым сообщением
    boolean isId = false;
    boolean isHealth = false;
    boolean isFeed = false;
    boolean isAction = false;


    @Override
    public boolean handle(Update update) {
//
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
                        ownerReport.setPetId(Long.parseLong(idMessage));
                        bot.execute(new SendMessage(upd.message().chat().id(), "ID питомца записан."));
                        isId = true;
                    } else {
                        bot.execute(new SendMessage(upd.message().chat().id(), "ID питомца указан неверно."));
                    }
                };
                break;
            case "/health":
                bot.execute(new SendMessage(update.message().chat().id(), "Опишите кратко самочувствие питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setPetsHealth(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Записано в отчёт!"));
                    isHealth = true;
                };
                break;

            case "/feed":
                bot.execute(new SendMessage(update.message().chat().id(), "Опишите рацион питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setNutrition(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Рацион питомца записан в отчёт!"));
                    isFeed = true;
                };
                break;
            case "/action":
                bot.execute(new SendMessage(update.message().chat().id(), "Опишите кратко поведение питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setPetsBehavior(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Записано в отчёт!"));
                    isAction = true;
                };
                break;

            case "/save":
                ownerReport.setReportDateTime(LocalDateTime.now());
                ownerReport.setOwnerId(update.message().chat().id());
                if (TelegramBotPetShelterUpdatesListener.dogSelect){
                    ownerReport.setPetsType(PetsType.DOG);
                } else if (TelegramBotPetShelterUpdatesListener.catSelect) {
                    ownerReport.setPetsType(PetsType.CAT);
                }
                reportService.save(ownerReport);
                bot.execute(new SendMessage(update.message().chat().id(), "Ваш отчёт загружен"));
                return true; // возвращаем true - это значит, что контекст завершен.

        }
        return false;
    }
}
