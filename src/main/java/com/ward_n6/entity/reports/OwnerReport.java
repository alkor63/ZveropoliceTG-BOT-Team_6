package com.ward_n6.entity.reports;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * имя
 * фото
 * рацион
 * поведение
 */
@AllArgsConstructor


@Getter
@Setter
@Entity
@Table(name = "owner_report")
// нужно делать класс абстрактным???
public class OwnerReport {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "report_date_time", nullable = false)
    private LocalDateTime reportDateTime; // дата, время

    @Column(name = "pet_type", nullable = false)
    private PetsType petsType;

    @Column(name = "have_a_photo")
    private boolean havePhoto; // проверка наличия фото

    @Column(name = "nutrition")
    private String nutrition; // питание

    @Column(name = "pets_health")
    private String petsHealth; // о здоровье

    @Column(name = "pets_behavior")
    private String petsBehavior; // поведение


    @Column(name = "pet_id", nullable = false)
    private long petId; // id питомца
    @Lob
    private List<BufferedImage> photos = new ArrayList<>(); // лист фоток

    public OwnerReport() {
    }

    public OwnerReport(long id, long chatId, LocalDateTime reportDateTime, PetsType petsType, boolean havePhoto, String nutrition,
                       String petsHealth, String petsBehavior, long petId) {
        this.id = id;
        this.chatId = chatId;
        this.reportDateTime = reportDateTime;
        this.petsType = petsType;

        if (photos.size() != 0) {
            this.havePhoto = true;
        }
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
    }

    public OwnerReport(long id, long chatId, LocalDateTime reportDateTime, PetsType petsType, boolean havePhoto, String nutrition,
                       String petsHealth, String petsBehavior, Pet ownersPet, long petId) {
        this.id = id;
        this.chatId = chatId;
        this.reportDateTime = reportDateTime;
        this.petsType = petsType;
        this.havePhoto = havePhoto;
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport that)) return false;
        return getId() == that.getId() && getChatId() == that.getChatId() && Objects.equals(getReportDateTime(), that.getReportDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getReportDateTime());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}