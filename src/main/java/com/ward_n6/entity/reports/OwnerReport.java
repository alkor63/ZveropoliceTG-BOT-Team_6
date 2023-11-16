package com.ward_n6.entity.reports;

import com.ward_n6.enums.PetsType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * имя
 * фото
 * рацион
 * поведение
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "owner_report")
// нужно делать класс абстрактным???
public class OwnerReport {

    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "report_date_time", nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportDateTime; // дата, время

    @Enumerated(EnumType.STRING) // для сохранения названия, а не цифры
    @Column(name = "pet_type", columnDefinition = "VARCHAR(255)")
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

//    @Column(name = "photo", nullable = false)
//    PhotoSize photo;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport report)) return false;
        return getId() == report.getId() && getOwnerId() == report.getOwnerId() && isHavePhoto() == report.isHavePhoto() && getPetId() == report.getPetId() && Objects.equals(getReportDateTime(), report.getReportDateTime()) && getPetsType() == report.getPetsType() && Objects.equals(getNutrition(), report.getNutrition()) && Objects.equals(getPetsHealth(), report.getPetsHealth()) && Objects.equals(getPetsBehavior(), report.getPetsBehavior());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwnerId(), getReportDateTime(), getPetsType(), isHavePhoto(), getNutrition(), getPetsHealth(), getPetsBehavior(), getPetId());
    }

    public long getOwnerId() {
        return ownerId;
    }

    public OwnerReport setOwnerId(long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public String toString() {
        return "OwnerReport{" +
                "id=" + ownerId +
                ", reportDateTime=" + reportDateTime +
                ", petsType=" + petsType +
                ", havePhoto=" + havePhoto +
                ", nutrition='" + nutrition + '\'' +
                ", petsHealth='" + petsHealth + '\'' +
                ", petsBehavior='" + petsBehavior + '\'' +
                ", petId=" + petId +
                '}';
    }
}