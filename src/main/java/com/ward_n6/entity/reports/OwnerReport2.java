package com.ward_n6.entity.reports;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
/**
 * класс для отработки БД
 * будет удалён в финальной версии
 */
@Getter
@Setter
//@Entity
@Table(name="owner_report2")
public class OwnerReport2 {
    @javax.persistence.Id
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "report_date_time", nullable = false)
    private LocalDateTime reportDateTime; // дата, время

    @Column(name = "have_a_photo", nullable = false)
    private boolean havePhoto; // проверка наличия фото

    @Column(nullable = false)
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

    @Column(name = "owner_id", nullable = false)
    private long ownerId; // id питомца

    public OwnerReport2() {
    }

    public OwnerReport2(boolean havePhoto, String nutrition, String petsHealth, String petsBehavior, long petId, long ownerId) {
        this.havePhoto = havePhoto;
        this.nutrition = nutrition;
        this.petsHealth = petsHealth;
        this.petsBehavior = petsBehavior;
        this.petId = petId;
        this.ownerId = ownerId;
        this.reportDateTime = LocalDateTime.now();
    }

//   фото
    //рацион
    //здоровье
    //изменение поведения

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerReport2 that = (OwnerReport2) o;
        return id == that.id && petId == that.petId && ownerId == that.ownerId && reportDateTime.equals(that.reportDateTime) && Objects.equals(nutrition, that.nutrition) && Objects.equals(petsHealth, that.petsHealth) && Objects.equals(petsBehavior, that.petsBehavior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportDateTime, nutrition, petsHealth, petsBehavior, petId, ownerId);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OwnerReport{" +
                "id=" + id +
                ", reportDateTime=" + reportDateTime +

                ", petId=" + petId + " id = " + ownerId +
                '}';
    }
}
