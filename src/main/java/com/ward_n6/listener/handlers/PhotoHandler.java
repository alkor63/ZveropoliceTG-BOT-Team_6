package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.entity.reports.Photo;
import com.ward_n6.enums.PetsType;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.reports.PhotoRepository;
import com.ward_n6.service.owners.OwnerReportServiceImpl;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import static com.ward_n6.listener.handlers.ReportHandler.petIdForPhoto;

@Component
public class PhotoHandler implements EventHandler {
    private final TelegramBot telegramBot;
    private final PhotoRepository photoRepository;
    private final Photo photos = new Photo();
    private OwnerReport ownerReport = new OwnerReport();
    private final OwnerReportServiceImpl ownerReportServiceImpl;

    public PhotoHandler(TelegramBot telegramBot, PhotoRepository photoRepository, OwnerReportServiceImpl ownerReportServiceImpl) {
        this.telegramBot = telegramBot;
        this.photoRepository = photoRepository;
        this.ownerReportServiceImpl = ownerReportServiceImpl;
    }
    String folderPath = "/Users/Stellareign/IdeaProjects/ZveropoliceTG-BOT-Team_Ward_N6/src/main/resources/photo"; //
    // путь к файлам (для мака свой, для винды - свой )
    @Override
    public boolean handle(Update update) {
        if (update.message() != null && (update.message().photo() != null || update.message().text() != null)) {
            var messagePhoto = update.message().photo(); //создаём переменную и присваиваем ей значение из сообщения
            if (messagePhoto != null && messagePhoto.length > 0) {
                var photo = update.message().photo()[1]; // 3 - самое лучшее качество
                var getFile = telegramBot.execute(new GetFile(photo.fileId())); // создаём переменную и
                // получаем файл вызовом метода execute(), передавая fileId из фото
                var outFile = new File(folderPath, (photo.fileId() + "photo_ownerID-" + update.message().chat().id() + ".jpeg")); // добавлено
                // создали файл outFile и передали ему в конструктор путь к папке и имя файла
                try (var in = new URL(telegramBot.getFullFilePath(getFile.file())).openStream(); // поток для чтения через URL
                     var out = new FileOutputStream(outFile)) { // открытие потока для записи
                    in.transferTo(out); // запись с помощью метода transferTo() (передача данных из чтения в поток записи)
                    savePhotoToDB(update, photo);

                    ReportHandler.isPhoto = true;
                    savePhotoToReport(update);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), // отправка сообщения в чат о загрузке фото
                            """
                                    Фото-отчёт загружен.
                                    \n Загрузить ещё фото: /photo
                                    \n Отправить текстовый отчёт: /report
                                    \n Вернуться к выбору приюта: /start
                                    """));
                    return true;
                    
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Вы не отправили фото. "));
            }
        }
        return false;
    }

    // ***************** методы сохранения ************************
    private OwnerReport savePhotoToReport (Update update){
        if (TelegramBotPetShelterUpdatesListener.dogSelect) {
            ownerReport.setPetsType(PetsType.DOG);
        } else if (TelegramBotPetShelterUpdatesListener.catSelect) {
            ownerReport.setPetsType(PetsType.CAT);
        }
        ownerReport.setHavePhoto(true);
        ownerReport.setPetId(petIdForPhoto);
        ownerReport.setOwnerId(update.message().chat().id());
        ownerReport.setReportDateTime(LocalDateTime.now());
        ownerReportServiceImpl.save(ownerReport);
        return ownerReport;
    }
    private Photo savePhotoToDB (Update update, PhotoSize photo){
        photos.setPhoto(photo);
        photos.setPetId(petIdForPhoto);
        photos.setDateTime(LocalDateTime.now());
        photos.setOwnerId(update.message().chat().id());
        photos.setFileName(photo.fileId() + "-owner-" + update.message().chat().id() + ".jpeg");
        photoRepository.save(photos);
        return photos;
    }
}


