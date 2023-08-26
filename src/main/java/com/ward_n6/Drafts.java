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

    //************************  PET_CONTROLLER****
    //        @PostMapping
//        @Operation(summary = "Добавление животного в список", description = "нужно заполнить все поля карточки животного в Body")
//        public ResponseEntity<Pet> createPet(@RequestBody Pet pet) throws JsonProcessingException, PutToMapException {
//            Pet newPet = petService.addPet(pet);
//            return ResponseEntity.ok(newPet);
//        }
    //    @PostMapping("/json")
//    @Operation(summary = "Добавление рецепта из файла в книгу", description = "будем читать файл pets.json")
//    public ResponseEntity<Pet> readPetFromJsonFile() {
//        return ResponseEntity.ok().build();
//    }
//        @GetMapping("/{petId}")
//        @Operation(summary = "Показать одно животное по id", description = "нужно указать id животного")
//        public ResponseEntity<Pet> getPet(@PathVariable int petId) {
//            Pet pet = petService.getPetById(petId);
//            if (pet == null) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok(pet);
//        }


    //**************************************** фОТО ******************************
//    @Value("${path.to.file}")
//    String folderPath; // путь к файлам
//
//    private void getPhoto(Update update) {
//        PhotoSize[] messagePhoto = update.message().photo(); // получаем сообщение из текущего обновления
//        Long chatId = update.message().chat().id(); // получаем идентификатор чата, к которому относится апдейт
////       PhotoSize[] messagePhoto = message.photo();
//        if (messagePhoto != null) {
//            var photo = update.message().photo()[3]; // 3 - самое лучшее качество
//            var getFile = telegramBot.execute(new GetFile(photo.fileId()));
//            var outFile = new File(folderPath, (photo.fileId() + "-owner-" + chatId + ".jpeg")); // добавлено
//            try (var in = new URL(telegramBot.getFullFilePath(getFile.file())).openStream();
//                 var out = new FileOutputStream(outFile)) {
//                // для примера просто сделал случайное название файла, лучше прописать путь и расширение
//                in.transferTo(out);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

}
