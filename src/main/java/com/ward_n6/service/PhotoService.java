package com.ward_n6.service;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PhotoService {
    public PhotoSize getPhoto() {
        Update update = new Update();
        // Проверяем, что в сообщении есть фото
        if (update.hasMessage() && update.getMessage().hasPhoto()) {

            List<PhotoSize> photos = update.getMessage().getPhoto(); // лист фото со всеми размерами фото

            // Забираем наибольший размер фото (полностью загружено)
            return photos.stream()
                    .max(Comparator // находим наибольшее фото
                            .comparing(PhotoSize::getFileSize))// возвращаем размер в байтах
                    .orElse(null); // если размера нет, возвращаем нулл

        }
        // или возвращаем нулл
        return null;
    }

    public PhotoSize getPhotos(Update update) {
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // Получаем наибольший размер фото
            PhotoSize photo = photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);

            if (photo != null) {
                try {
                    // Получение фото по URL
                    URL url = new URL(photo.getFilePath());
                    InputStream inputStream = url.openStream();

                    // Генерация имени файла с помощью UUID
                    String fileName = UUID.randomUUID().toString() + ".jpg";

                    // Указываем путь к папке resources/photo
                    Path outputPath = Paths.get("src/main/resources/photos/" + fileName);

                    // Сохраняем фото в указанной папке
                    Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);

                    return photo;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}