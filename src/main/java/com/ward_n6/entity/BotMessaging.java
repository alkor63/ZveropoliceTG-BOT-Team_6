package com.ward_n6.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Getter
@Setter
// класс для обработки сообщения и создания базы переписки с хозяином
@Entity
@Table(name = "correspondence_with_owner")
public class BotMessaging {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    private String userName;


    @Column(name = "message", nullable = false)
    private String botMessage;

    @Column(name = "message_date_time", nullable = false)
    private LocalDateTime massageDateTime = LocalDateTime.now();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BotMessaging)) return false;
        BotMessaging that = (BotMessaging) o;
        return getChatId() == that.getChatId() && Objects.equals(getBotMessage(), that.getBotMessage()) && Objects.equals(getMassageDateTime(), that.getMassageDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getBotMessage(), getMassageDateTime());
    }



    @Override
    public String toString() {
        return "Переписка " +
                "chatId=" + chatId +
                " Сообщение " + botMessage + '\'' +
                ", дата и время: " + massageDateTime +
                '}';
    }

}
