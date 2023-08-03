package entity.shelters;

import entity.pets.Pet;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;
// абстрактный класс для реализации конструктора и методов приюта
public abstract class PetShelter {
    private String shelterName = "Приют «Мечта»"; //название приюта
    private String description = "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам! " +
            "Скажите, ваш вопрос связан с приютом для кошек или для собак?"; //описание приюта
    private String shelterAddress = "«Мечта» и его жители находятся по адресу: ул. Защитников Животных д.1. Мы открыты для посещений каждый день с 11:00 до 18:00."; //адрес, режим работы
    private String securityContact = "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы."; //контактные данные охраны
    private String safetyAdvice = "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках."; //рекомендации о технике безопасности на территории приюта
    private String helpShelter = "<b>Приют нуждается в физической и материальной помощи.</b> Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д."; //помощь приюту
    private String takePet = "Приветствуем вас! Прежде чем оформить документы, необходимо познакомиться с питомцем. Встреча и общение с животным проходят в присутствии и под наблюдением ответственного сотрудника приюта. Для оформления договора потребуется ваш паспорт РФ. Испытательный срок составляет 30 дней. В течение этого срока хозяин обязан присылать ежедневные отчеты (в чат-бот, на электронную почту, на WhatsApp и пр.). Выбранный способ отчетности также фиксируется в договоре. "; //общее описание, у кошек и собак добавятся особенности
    private String prohibitedTakePet = "<b>Почему мы можем отказать в усыновлении?</b>" +
            "1.\tЖивотное не пошло с вами на контакт.\n" +
            "2.\tУсловия будущего проживания с вами не соответствуют необходимым для конкретного питомца.\n" +
            "3.\tСемейные разногласия – нередко один член семьи готов завести питомца, а другой (другие) против. В таком случае мы отказываем.\n" +
            "4.\tОтказ усыновителя оформить документы и пройти испытательный срок. Желание ускорить процесс будет для нас «красным маркером».\n" +
            "5.\tЧасто мы отказываем в усыновлении, если в семье уже имеется какое-нибудь животное или несколько животных. В зависимости от характера нашего питомца, мы пристраиваем его в семьи без животных, либо с животными.\n";

    private String callVolonteer = "Через некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы";

    public PetShelter() {

    }
    //геттеры и сеттеры
    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterAddress() {
        return shelterAddress;
    }

    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }

    public String getSafetyAdvice() {
        return safetyAdvice;
    }

    public void setSafetyAdvice(String safetyAdvice) {
        this.safetyAdvice = safetyAdvice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecurityContact() {
        return securityContact;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public String getHelpShelter() {
        return helpShelter;
    }

    public void setHelpShelter(String helpShelter) {
        this.helpShelter = helpShelter;
    }

    public String getCallVolonteer() {
        return callVolonteer;
    }

    public void setCallVolonteer(String callVolonteer) {
        this.callVolonteer = callVolonteer;
    }

    public String getTakePet() {
        return takePet;
    }

    public void setTakePet(String takePet) {
        this.takePet = takePet;
    }

    public String getProhibitedTakePet() {
        return prohibitedTakePet;
    }

    public void setProhibitedTakePet(String prohibitedTakePet) {
        this.prohibitedTakePet = prohibitedTakePet;
    }

    private Map<Integer, Pet> petsInShelter = new HashMap<Integer, Pet>(); // список питомцев в приюте

    public PetShelter(String shelterName, String shelterAdress, Map<Integer, Pet> petsInSelter) {
        this.shelterName = shelterName;
        this.shelterAddress = shelterAdress;
        this.petsInShelter = petsInSelter;
    }


    public Map<Integer, Pet> getPetsInShelter() {
        return petsInShelter;
    }

    public PetShelter setPetsInShelter(Map<Integer, Pet> petsInSelter) {
        this.petsInShelter = petsInSelter;
        return this;
    }


}
