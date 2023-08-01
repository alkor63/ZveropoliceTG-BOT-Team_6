package entity.reports;

import entity.pets.Pet;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
// нужно делать класс абстрактным???
public class OwnerReport {
    private LocalDateTime dateTime;
    private Image photo;
    private String nutrition;
    private String health;
    private String behavior;
    private long ownerId;
    private Pet pet;

    public OwnerReport() {
    }

    public OwnerReport(Pet pet) {
        this.pet = pet;
    }
    // фото
    //рацион
    //здоровье
    //изменение поведения - м.б. null?


    public OwnerReport(LocalDateTime dateTime, Image photo, String nutrition, String health, String behavior, long ownerId, Pet pet) {
        this.dateTime = dateTime;
        this.photo = photo;
        this.nutrition = nutrition;
        this.health = health;
        this.behavior = behavior;
        this.ownerId = ownerId;
        this.pet = pet;
    }

    public OwnerReport(Pet pet, long ownerId, LocalDateTime dateTime) {
        this.pet = pet;
        this.ownerId = ownerId;
        this.dateTime = dateTime;
    }

    public Pet getPet() {
        return pet;
    }

    public OwnerReport setPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport)) return false;
        OwnerReport that = (OwnerReport) o;
        return Objects.equals(getPet(), that.getPet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPet());
    }
}
