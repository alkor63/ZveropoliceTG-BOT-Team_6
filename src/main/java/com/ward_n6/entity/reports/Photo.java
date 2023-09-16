package com.ward_n6.entity.reports;

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
@Table(name = "photos")

public class Photo {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="file_name")
    private String fileName;

    @Lob
    @Column(name = "photo", nullable = false)
    private PhotoSize photo;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "owner_Id")

    private long ownerId;

    @Column(name = "pet_Id")

    private long petId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo photo1)) return false;
        return getId() == photo1.getId() && getOwnerId() == photo1.getOwnerId() && getPetId() == photo1.getPetId() && Objects.equals(getFileName(), photo1.getFileName()) && Objects.equals(getPhoto(), photo1.getPhoto()) && Objects.equals(getDateTime(), photo1.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileName(), getPhoto(), getDateTime(), getOwnerId(), getPetId());
    }
}
