package com.ward_n6.entity.reports;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
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
@Builder
//@Data
@Entity
@Table(name = "owner_report")
// нужно делать класс абстрактным???
public class OwnerReport {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "report_date_time", nullable = false)
    private LocalDateTime reportDateTime; // дата, время

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
    @Column(name = "owner_id", nullable = false)
    private long ownerId; // id [усыновителя]
    public OwnerReport() {
    }

    public OwnerReport(long id, LocalDateTime reportDateTime, boolean havePhoto, String nutrition,
                       String petsHealth, String petsBehavior, long petId) {
        this.id = id;
        this.reportDateTime = reportDateTime;
        this.havePhoto = havePhoto;
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerReport that = (OwnerReport) o;
        return id == that.id && havePhoto == that.havePhoto && petId == that.petId && ownerId == that.ownerId && Objects.equals(reportDateTime, that.reportDateTime) && Objects.equals(nutrition, that.nutrition) && Objects.equals(petsHealth, that.petsHealth) && Objects.equals(petsBehavior, that.petsBehavior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportDateTime, havePhoto, nutrition, petsHealth, petsBehavior, petId, ownerId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OwnerReport{" +
                "id=" + id +
                ", reportDateTime=" + reportDateTime +

                ", petId=" + petId +
                '}';
    }
}