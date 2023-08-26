package com.ward_n6;

import com.ward_n6.repository.owner.OwnerRepository;

import java.util.regex.Pattern;

public class Drafts {
    private final OwnerRepository ownerRepository;

    private static final Pattern OWNER_DATA_PATTERN = Pattern.compile("([А-я])\\s|\\n + ([А-я]) \\s|\\n +" +
            "(\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2})"); //символы и \\s - пробел, в (...) - группы паттерна

    public Drafts(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

//    // СОХРАНЕНИЕ ПЕРСОНЫ в БД (не используем)
//    private void saveOwner(long chatId, String messageText) {
//        Matcher matcher = OWNER_DATA_PATTERN.matcher(messageText); // поиск соответствия сообщения заданным параметрам
//        if (matcher.find()) {
//            Owner owner = new Owner();
//            String ownerLastName = matcher.group(1);
//            String ownerFirstName = matcher.group(2);
//            String ownerPhone = matcher.group(3);
//            owner.setLastName(ownerLastName);
//            owner.setFirstName(ownerFirstName);
//            owner.setPhoneNumber(ownerPhone);
//            ownerRepository.save(owner);
//            sendMessage(chatId, "Ваши данные добавлена в нашу базу: " + owner.toString() +
//                    "Для удаления Ваших данных из нашей базы обратитесь к волонтёру");
//        } else {
//            sendMessage(chatId, "Данные указаны неверно. Пожалуйста, введите Ваши данные в указанном формате.");
//        }
//    }

    //************************************************

//    private void sendOwnerHowReport(long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_REPORT));
//        InlineKeyboardButton textButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить текст :memo:"));
//        InlineKeyboardButton photoButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить фото :camera:"));
//        textButton.callbackData("ОТЧЁТ");
//        photoButton.callbackData("ОТЧЁТ_ФОТО");
//        Keyboard keyboard = new InlineKeyboardMarkup().addRow(textButton).addRow(photoButton);
//        sendMessage.replyMarkup(keyboard);
//
//    }

}
