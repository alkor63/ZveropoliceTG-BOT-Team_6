package entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
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

    @Column(name = "message", nullable = false)
    private String botMessage;

    @Column(name = "message_date_time")
    private LocalDateTime massageDateTime;


    public Long getChatId() {
        return chatId;
    }

    public String getBotMessage() {
        return botMessage;
    }

    public LocalDateTime getMassageDateTime() {
        return massageDateTime;
    }

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

    public BotMessaging setChatId(long chatId) {
        this.chatId = chatId;
        return this;
    }

    public BotMessaging setBotMessage(String botMessage) {
        this.botMessage = botMessage;
        return this;
    }

    public BotMessaging setMassageDateTime(LocalDateTime massageDateTime) {
        this.massageDateTime = massageDateTime;
        return this;
    }

    @Override
    public String toString() {
        return "Переписка " +
                "chatId=" + chatId +
                " Сообщение " + botMessage + '\'' +
                ", дата и время: " + massageDateTime +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
