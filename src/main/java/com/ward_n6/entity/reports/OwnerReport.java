package com.ward_n6.entity.reports;

import com.ward_n6.entity.pets.Pet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="owner_report")
// нужно дклать класс абстрактным???
public class OwnerReport {
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "report_date_time", nullable = false)
    private LocalDateTime reportDateTime; // дата, время

    @Column(name = "have_a_photo", nullable = false)
    private boolean havePhoto; // проверка наличия фото

    @Column(name = "nutrition", nullable = false)
    private String nutrition; // питание

    @Column(name = "pets_health", nullable = false)
    private String petsHealth; // о здоровье

    @Column(name = "pets_behavior", nullable = false)
    private String petsBehavior; // поведение

    //    @Column(name = "owners_pet", nullable = false)
//    private Pet ownersPet = new Pet() { // питомец - вывод для БД: id, тип, имя, возраст, порода -> проверить
//        @Override
//        public String toString() {
//            return super.reportToString();
//        }
//    };
    @Column(name = "pet_id", nullable = false)
    private long petId; // id питомца

    public OwnerReport() {
    }

    public OwnerReport(long id, LocalDateTime reportDateTime, boolean havePhoto, String nutrition, String petsHealth, String petsBehavior, long petId) {
        this.id = id;
        this.reportDateTime = reportDateTime;
        this.havePhoto = havePhoto;
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
    }

    public OwnerReport(long id, LocalDateTime reportDateTime, boolean havePhoto, String nutrition, String petsHealth, String petsBehavior, Pet ownersPet, long petId) {
        this.id = id;
        this.reportDateTime = reportDateTime;
        this.havePhoto = havePhoto;
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
    }

// фото
    //рацион
    //здоровье
    //изменение поведения - м.б. null?


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport that)) return false;
        return getId() == that.getId() && isHavePhoto() == that.isHavePhoto() && getPetId() == that.getPetId() && Objects.equals(getReportDateTime(), that.getReportDateTime()) && Objects.equals(getNutrition(), that.getNutrition()) && Objects.equals(getPetsHealth(), that.getPetsHealth()) && Objects.equals(getPetsBehavior(), that.getPetsBehavior());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReportDateTime(), getPetId());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}