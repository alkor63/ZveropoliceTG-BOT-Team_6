package com.ward_n6.entity.owners;

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

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;


    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetsOwner petsOwner)) return false;
        return getOwnerId() == petsOwner.getOwnerId() && getPetId() == petsOwner.getPetId() && Objects.equals(getId(), petsOwner.getId()) && Objects.equals(getStartDate(), petsOwner.getStartDate()) && Objects.equals(getEndDate(), petsOwner.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwnerId(), getPetId(), getStartDate(), getEndDate());
    }

    @Override
    public String toString() {
        return "PetsOwner{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", petId=" + petId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

