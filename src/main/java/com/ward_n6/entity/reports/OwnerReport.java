package com.ward_n6.entity.reports;

import com.ward_n6.enums.PetsType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
//@Data
@Entity
@Table(name = "owner_report")
// нужно делать класс абстрактным???
public class OwnerReport {
    @javax.persistence.Id
//    @Column(name = "id", nullable = false)
    @Id
  //  @GeneratedValue(strategy = GenerationType.class)
 //   private long id;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "report_date_time", nullable = false)
    private LocalDateTime reportDateTime; // дата, время

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

//    @Column(name = "owner_id", nullable = false)
//    private long ownerId; // id [усыновителя]
//    public OwnerReport() {
//    }

//    public OwnerReport(long ownerId, LocalDateTime reportDateTime, PetsType petsType, boolean havePhoto, String nutrition,
//                       String petsHealth, String petsBehavior, long petId) {
//        this.ownerId = ownerId;
//        this.reportDateTime = reportDateTime;
//        this.havePhoto = havePhoto;
//        this.nutrition = nutrition;
//        this.petsHealth = petsHealth;
//        this.petsBehavior = petsBehavior;
//        this.petId = petId;
//        this.petsType = petsType;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport that)) return false;
        return getOwnerId() == that.getOwnerId() && isHavePhoto() == that.isHavePhoto() && getPetId() == that.getPetId() && Objects.equals(getReportDateTime(), that.getReportDateTime()) && getPetsType() == that.getPetsType() && Objects.equals(getNutrition(), that.getNutrition()) && Objects.equals(getPetsHealth(), that.getPetsHealth()) && Objects.equals(getPetsBehavior(), that.getPetsBehavior());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwnerId(), getReportDateTime(), getPetsType(), isHavePhoto(), getNutrition(), getPetsHealth(), getPetsBehavior(), getPetId());
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
                "ownerId=" + ownerId +
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