package com.ward_n6.entity;

import com.pengrad.telegrambot.model.PhotoSize;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
/**
 * класс - попытка сохранить хоть как-то фото
 */
@Component
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "Photos")

public class Photo {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(name = "photo", nullable = false)
    PhotoSize photo;

    @Column(name = "date_time", nullable = false)
    LocalDateTime dateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo photo1)) return false;
        return getId() == photo1.getId() && Objects.equals(getPhoto(), photo1.getPhoto()) && Objects.equals(getDateTime(), photo1.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhoto(), getDateTime());
    }
}
