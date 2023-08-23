package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "pets_owner")

public class PetsOwner {

    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "pet_id", nullable = false)
    private long petId;

//    @ManyToOne
    @Transient
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private Owner owner;

    @Column(name = "pet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetsType petsType;

//    @ManyToOne
    @Transient
    @JoinColumn(name = "pet", insertable = false, updatable = false)
    private Pet pet;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;



    @Column(name = "owner") // заносим сущность в строковом виде в таблицу
    public String getOwnerAsString() {
        return owner != null ? owner.toString() : null;
    }

    @Column(name = "pet")
    public String getPetAsString() {
        return pet != null ? pet.toString() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetsOwner petsOwner)) return false;
        return getOwnerId() == petsOwner.getOwnerId() && getPetId() ==
                petsOwner.getPetId() && Objects.equals(getId(), petsOwner.getId())
                && Objects.equals(getOwner(), petsOwner.getOwner())
                && getPetsType() == petsOwner.getPetsType()
                && Objects.equals(getPet(), petsOwner.getPet())
                && Objects.equals(getStartDate(), petsOwner.getStartDate())
                && Objects.equals(getEndDate(), petsOwner.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwnerId(), getPetId(), getOwner(), getPetsType(),
                getPet(), getStartDate(), getEndDate());
    }

    @Override
    public String toString() {
        return "PetsOwner{" +
                "id=" + id +
                ", owner=" + owner +
                ", pet=" + pet +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

