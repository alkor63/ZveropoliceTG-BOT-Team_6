package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Pet;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
// абстрактный класс для реализации конструктора и методов приюта
@Getter

public abstract class PetShelter {
    private String shelterName = "Приют «Мечта»"; //название приюта
    @Getter
    public String description = "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам! ";

    //"Скажите, ваш вопрос связан с приютом для кошек или для собак?"; //описание приюта

    private String dogShelterAddress = "«Мечта» и его собаки находятся по адресу: ул. Защитников Животных д.1. Мы открыты для посещений каждый день с 11:00 до 18:00."; //адрес, режим работы
    private String catShelterAddress = "«Мечта» и его кошки находятся по адресу: ул. Защитников Животных д.1. Мы открыты для посещений каждый день с 11:00 до 18:00."; //адрес, режим работы
    private String securityContact = "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы."; //контактные данные охраны
    private String safetyAdvice = "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках."; //рекомендации о технике безопасности на территории приюта
    @Getter
    private String helpShelter = "Приют нуждается в физической и материальной помощи. Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д."; //помощь приюту
    @Getter
    private String takePet = "Рады, что вы заинтересованы стать обладателем питомца! Прежде чем оформить документы, необходимо познакомиться с ним. Встреча и общение с животным проходят в присутствии и под наблюдением ответственного сотрудника приюта. Для оформления договора потребуется ваш паспорт РФ. Испытательный срок составляет 30 дней. В течение этого срока хозяин обязан присылать ежедневные отчеты (в чат-бот, на электронную почту, на WhatsApp и пр.). Выбранный способ отчетности также фиксируется в договоре. "; //общее описание, у кошек и собак добавятся особенности
    @Getter
    private String prohibitedTakePet = ":no_entry:Почему мы можем отказать в усыновлении?:no_entry:\n" +
            ":one:\tЖивотное не пошло с вами на контакт.\n" +
            ":two:\tУсловия будущего проживания с вами не соответствуют необходимым для конкретного питомца.\n" +
            ":three:\tСемейные разногласия – нередко один член семьи готов завести питомца, а другой (другие) против. В таком случае мы отказываем.\n" +
            ":four:\tОтказ усыновителя оформить документы и пройти испытательный срок. Желание ускорить процесс будет для нас «красным маркером».\n" +
            ":five:\tЧасто мы отказываем в усыновлении, если в семье уже имеется какое-нибудь животное или несколько животных. В зависимости от характера нашего питомца, мы пристраиваем его в семьи без животных, либо с животными.\n";
    @Getter
    private String callVolonteer = ":white_check_mark:Через некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы!:wink:";

    private String reportInfo = "Для отчёта нужно отправить текст :memo: описывающий: самочувствие питомца, рацион питания питомца, в каких условиях живёт питомец; и фото питомца :camera:";

    public PetShelter() {

    }
    //геттеры и сеттеры
    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public void setDogShelterAddress(String dogShelterAddress) {
        this.dogShelterAddress = dogShelterAddress;
    }

    public void setCatShelterAddress(String catShelterAddress) {
        this.catShelterAddress = catShelterAddress;
    }

    public String getSafetyAdvice() {
        return safetyAdvice;
    }

    public void setSafetyAdvice(String safetyAdvice) {
        this.safetyAdvice = safetyAdvice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public void setHelpShelter(String helpShelter) {
        this.helpShelter = helpShelter;
    }

    public void setCallVolonteer(String callVolonteer) {
        this.callVolonteer = callVolonteer;
    }

    public void setTakePet(String takePet) {
        this.takePet = takePet;
    }

    public void setProhibitedTakePet(String prohibitedTakePet) {
        this.prohibitedTakePet = prohibitedTakePet;
    }

    @Getter
    private Map<Integer, Pet> petsInShelter = new HashMap<Integer, Pet>(); // список питомцев в приюте

    public PetShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInSelter) {
        this.shelterName = shelterName;
        this.dogShelterAddress = shelterAddress;
        this.catShelterAddress = shelterAddress;
        this.petsInShelter = petsInSelter;
    }


    public PetShelter setPetsInShelter(Map<Integer, Pet> petsInSelter) {
        this.petsInShelter = petsInSelter;
        return this;
    }


}
