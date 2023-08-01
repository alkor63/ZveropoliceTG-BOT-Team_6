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

public class Volunteer { // класс волонтёра, возможно он должен быть интерфейсом
    // метод добавление усыновителя в БД - где берём учётные данные?
    // создание листа питомцев усыновителя: можно задействовать енум питомцев, ограничить количество питомцев, например, не более 5
    // метод беседы с усыновителем
    // номер телефона как ID ????
    // метод просмотр отчётов
    // отслеживание испыт. срока

    // выдаёт рекомендации по содержанию - это будет отдельный файл (?), с которого будут считываться рекомендации,
    //наверное, не очень логично писать текст прямо внутри метода
}
