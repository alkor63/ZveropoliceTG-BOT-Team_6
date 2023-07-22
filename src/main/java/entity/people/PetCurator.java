package entity.people;

import entity.pets.Pet;

import java.util.List;
// Здесь, возможно, будет куратор, за которым закреплены животные
public class PetCurator {
    private long id;
    private long curatorId;
    private String lastName;
    private String fistName;
    private List<Pet> curatorsPets;

}
