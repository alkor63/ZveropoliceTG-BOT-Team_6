package com.ward_n6.entity.owners;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "pet_id", nullable = false)
    private long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetsOwner petsOwner)) return false;
        return getId() == petsOwner.getId() && Objects.equals(getOwnerId(), petsOwner.getOwnerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwnerId());
    }

    @Override
    public String toString() {
        return "PetsOwner{" +
                "petId=" + id +
                ", ownerId=" + ownerId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

