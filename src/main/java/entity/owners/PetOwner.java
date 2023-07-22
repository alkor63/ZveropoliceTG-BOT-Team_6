package entity.owners;
import entity.pets.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "pet_owners")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)
public abstract class PetOwner { // хозяин животного, его свойства
// закончился испыталка? что делать
    // через сколько времени удалять из базы
    // нужен ли чёрный список усыновителей

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

    @Column(name = "owner_id",nullable = false)
    private long ownerId;



    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last-name")
    private String lastName;

    @Column(name = "owner_phone")
    private String phoneNumber;

    private String eMale;

    @Column(name = "adoption_start_date")
    private LocalDateTime dateOfPetAdoption;
    private LocalDateTime endOfOwnerProbationPeriod;
    private boolean probationPeriodIsPositive;

   @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> petOwnersList = new ArrayList<>();

    public PetOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.petOwnersList = petOwnersList;
    }


    public Long getId() {
        return id;
    }
    public long getOwnerId() {
        return ownerId;
    }

    public PetOwner setOwnerId(int ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PetOwner setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PetOwner setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<Pet> getPetOwnersList() {
        return petOwnersList;
    }

    public PetOwner setPetOwnersList(List<Pet> petOwnersList) {
        this.petOwnersList = petOwnersList;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PetOwner setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEMale() {
        return eMale;
    }

    public PetOwner setEMale(String eMale) {
        this.eMale = eMale;
        return this;
    }

    public LocalDateTime getDateOfPetAdoption() {
        return dateOfPetAdoption;
    }


    public LocalDateTime getEndOfOwnerProbationPeriod() {
        return endOfOwnerProbationPeriod;
    }

    public PetOwner setEndOfOwnerProbationPeriod(LocalDateTime endOfOwnerProbationPeriod) {
        this.endOfOwnerProbationPeriod = endOfOwnerProbationPeriod;
        return this;
    }



    public boolean isProbationPeriodIsPositive() {
        return probationPeriodIsPositive;
    }

    public PetOwner setProbationPeriodIsPositive(boolean probationPeriodIsPositive) {
        this.probationPeriodIsPositive = probationPeriodIsPositive;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetOwner)) return false;
        PetOwner petOwner = (PetOwner) o;
        return getOwnerId() == petOwner.getOwnerId() && Objects.equals(getFirstName(), petOwner.getFirstName()) && Objects.equals(getLastName(), petOwner.getLastName()) && Objects.equals(getPhoneNumber(), petOwner.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwnerId(), getFirstName(), getLastName(), getPhoneNumber());
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "ownerId=" + ownerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", eMale='" + eMale +
                ", pets=" + petOwnersList + '\'' +
                '}';
    }



}
