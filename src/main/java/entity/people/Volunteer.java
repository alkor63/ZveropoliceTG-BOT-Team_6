package entity.people;
public class Volunteer {
    // класс с функционалом волонтёра
    // метод добавление усыновителя+животного в БД == createCatsOwner и createDogsOwner
    // метод беседы с усыновмтелем == callVolunteer
    // номер телефона как ID ???? - нет, только для связи
    // метод просмотр отчётов (точнее подсчета) == viewAllReports
    // Бот отслеживает испытательный срок и если срок подошел к концу - обращается к волонтёру за приговором
    // приговор в методе endOfProbationPeriod
    // вызов этого метода из Бота и его обработка - ниже, в ownersVerdict

    public String callVolunteer(String firstName) {
        // Волонтёр откликается на просьбу о связи от льзователя с userId
        return "Привет, " + firstName + "! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы";
    }

    public CatOwner createCatsOwner(Owner owner, Cat cat) {
        // собираем в одну таблицу усыновителя, животное cat и время начала и окончания испытательного срока
        return new CatOwner(owner.getOwnerId(),owner.getFirstName(), owner.getLastName(), owner.getPhoneNumber(),
                cat.getId(), LocalDate.now(), LocalDate.now().plusDays(30));
    }

    public DogOwner createDogsOwner(Owner owner, Dog dog) {
        // собираем в одну таблицу усыновителя, животное dog и время начала и окончания испытательного срока
        return new DogOwner(owner.getOwnerId(),owner.getFirstName(), owner.getLastName(), owner.getPhoneNumber(),
                dog.getId(), LocalDate.now(), LocalDate.now().plusDays(30));
    }

    public int viewAllReports(LocalDate date){
        // просмотр всех отчеиов за пршедшие сутки (с 21:00 предыдущего дня по 21:00 дня = date
        int num = 0;
        LocalTime time = LocalTime.of(21, 00);
        // запрос на просмотр отчетов может прийти, например, в 21:01
        // чтоб не потерять отчеты, пришедшие с 21:00 по 21:01, время задаём здесь
        LocalDateTime startTime = LocalDateTime.of(date.minusDays(1), time);
        LocalDateTime stopTime = LocalDateTime.of(date, time).plusNanos(1); // т.к. методы isAfter и isBefore не включают equals
        List<OwnerReport> ownerReportList = new ArrayList<>();
        for (OwnerReport ownerReport:ownerReportList) {
            LocalDateTime dateTime = ownerReport.getDateTime();
            if (dateTime.isAfter(startTime) && dateTime.isBefore(stopTime))
                num++; // есть отчёт в искомом интервале времени
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        System.out.println("c " + startTime.format(fmt)+" по "+stopTime.format(fmt)+" поступило "+num+" отчетов усыновителей");
        if (num > 0) System.out.println("все отчеты обработаны");
        return num; // количество отчетов за 24 часа до 21:00 указанной даты
    }

    public int endOfProbationPeriod(long petId, long ownerId) {

// исходим из того, что запрос на "приговор" приходит в день окончагия испытательношо срока
// т.е. дата = localDate.now()
// просматриваем все отчеты этого усыновителя за испытательный период и присваиваем ему рейтинг
        int numReport = 0;
        int numOldReport = 0;
        LocalDate today = LocalDate.now();
        LocalDate before30 = today.minusDays(30);
        LocalDate before60 = today.minusDays(61);
        // list всех отчетов
        List<OwnerReport> ownerReportList = new ArrayList<>();
        // forich для всех отчетов
        for (OwnerReport ownerReport : ownerReportList) {
            LocalDate date = ownerReport.getDateTime().toLocalDate();
            if (ownerReport.getPet().getPetId() == petId && ownerReport.getOwnerId() == ownerId) {
                if(date.isBefore(before30) && date.isAfter(before60)) {
                    numOldReport++;
                    break;
                }
                if (date.isAfter(before30.minusDays(1))) numReport++;
            }
        }
        if (numReport == 0) return 0;
        else if (numReport <= 10 && numOldReport == 0) return 1;
        else if (numReport <= 20 && numOldReport == 0) return 2;
        else return 3;
    }

    // метод обработки "приговора", вызывающий endOfProbationPeriod() - перенести в listener Боту
    public void ownersVerdict(long petId, long ownerId) {
        int rating = endOfProbationPeriod(petId, ownerId);
        switch (rating) {
            case 0:
                // вернуть животное в приют
                System.out.println("За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!");
                break;
            case 1:
                // продлить испытательный срок на 30 дней
                System.out.println("Вы очень редко присылали отчетыю Испытательный срок продлен на 30 дней");
                break;
            case 2:
                // продлить испытательный срок на 14 дней
                System.out.println("Вы присылали отчеты не регулярно. Испытательный срок продлен на 14 дней");
                break;
            case 3:
                // поздравить с успешным прохождением испытательношо срока
                System.out.println("Поздравляем с завершением испытательного срока! Ваш питомец остаётся с Вами навсегда");
                break;
            default:
                System.out.println("Некорректное значение rating. Разработчик что-то накосячил");
        }
    }
}
