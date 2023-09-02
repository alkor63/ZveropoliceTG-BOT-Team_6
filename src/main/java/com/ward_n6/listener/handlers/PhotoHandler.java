package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.Photo;
import com.ward_n6.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import static com.ward_n6.listener.handlers.ReportHandler.petIdForPhoto;


public class PhotoHandler implements EventHandler {
    private final TelegramBot telegramBot;
    private final PhotoRepository photoRepository;
    private final Photo photos = new Photo();

    public PhotoHandler(TelegramBot telegramBot, PhotoRepository photoRepository) {
        this.telegramBot = telegramBot;
        this.photoRepository = photoRepository;
    }

    @Value("${path.to.file}")
    String folderPath; // путь к файлам

//    private Consumer<Update> actionOnNextMessage;

    @Override
    public boolean handle(Update update) {

//        telegramBot.execute(new SendMessage(update.message().chat().id(),
//                "Загрузите фото для отчёта"));
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
                telegramBot.execute(new SendMessage(update.message().chat().id(), // отправка сообщения в чат о загрузке фото
                        "Фото загружено."));

                photos.setPhoto(photo);
                photos.setPetId(petIdForPhoto);
                photos.setDateTime(LocalDateTime.now());
                photos.setOwnerId(update.message().chat().id());
                photos.setFileName(photo.fileId() + "-owner-" + update.message().chat().id() + ".jpeg");
                photoRepository.save(photos);
                ReportHandler.isPhoto = true;
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Вы не отправили фото. "));
        }
        return false;

    }
}


