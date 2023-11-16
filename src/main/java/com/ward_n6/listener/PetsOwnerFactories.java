package com.ward_n6.listener;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import static com.ward_n6.factory.HibernateSessionFactoryUtil.getSessionFactory;
import static com.ward_n6.listener.TelegramBotPetShelterUpdatesListener.catSelect;
import static com.ward_n6.listener.TelegramBotPetShelterUpdatesListener.dogSelect;

/**
 *сессии для проверки наличия записей в БД при создании сущностей из бота
 */
@Component
public class PetsOwnerFactories {
    private final ChatMessager chatMessager;
    public PetsOwnerFactories(ChatMessager chatMessager) {
        this.chatMessager = chatMessager;
    }

    public Cat catFactory(long petId, long chatId) {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = getSessionFactory().openSession();
            Cat cat = session.get(Cat.class, petId);
            if (cat == null && catSelect) {
                chatMessager.sendMessage(chatId, "Такой кошки нет в приюте, проверьте ID питомца. " +
                        "\n /ID");
                session.close();
            } else
                session.close();
            return cat;
    }

    public Dog dogFactory(long petId, long chatId) {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = getSessionFactory().openSession();
            Dog dog = session.get(Dog.class, petId);
            if (dog == null && dogSelect) {
                chatMessager.sendMessage(chatId, "Такой собаки нет в приюте, проверьте ID животного." +
                        "\n /ID");
                session.close();
            } else
                session.close();
            return dog;
    }


    public Owner ownerFactory(Long ownerId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = getSessionFactory().openSession();
        Owner owner = session.get(Owner.class, ownerId);
        if (owner == null) {
            chatMessager.sendMessage(ownerId, "Отсутствует регистрация в приюте");
            session.close();
        } else
            session.close();
        return owner;
    }

    public PetsOwner petsOwnerFactory(Long petsOwnerId) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = getSessionFactory().openSession();
        PetsOwner petsOwner = session.get(PetsOwner.class, petsOwnerId);
        if (petsOwner != null) {
            session.close();
        } return petsOwner;
    }

    public ChatMessager getChatMessager() {
        return chatMessager;
    }

}
