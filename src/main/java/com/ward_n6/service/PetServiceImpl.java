package com.ward_n6.service;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.repository.pets.PetBaseRepository;
import com.ward_n6.service.interfaces.PetService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Repository
public class PetServiceImpl implements PetService {
    private PetBaseRepository petBaseRepository;
    private Pet cat = new Cat();
    private Pet dog = new Dog();
    private CatRepository catRepository;
    private DogRepository dogRepository;

    private Map<Integer, Pet> petMap = new HashMap<>();
    private int mapId = 0;
    @Override
    public Pet addPet(Pet pet) {
        if (pet.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить pet=Null в МАПу petMap");
        petMap.putIfAbsent(mapId++, pet);
        return pet;
    }



    @Override
    public Pet editPetById(int recordId, Pet pet) throws EditMapException {
        if (petMap.containsKey(recordId)) {
            petMap.put(recordId, pet);
            if (!petMap.get(recordId).equals(pet)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись о животном "
                        +pet.getBread()+" "+pet.getPetName()+" в МАПе petMap под id="+recordId);
            }
            return null;
        }
        return petMap.get(recordId);
    }




    @Override
    public boolean deletePetById(int recordId) throws DeleteFromMapException {
        if (petMap.containsKey(recordId)) {
            petMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о животном в МАПе petMap под id="+recordId);
    }
    @Override
    public boolean deletePetByValue(Pet pet) throws DeleteFromMapException {
        if (petMap.containsValue(pet)) {
            petMap.values().remove(pet);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о животном "
                +pet.getBread()+" "+pet.getPetName()+" в МАПе petMap");
    }
    @Override
    public int idPetByValue(Pet pet) {
        for (Map.Entry<Integer, Pet> entry : petMap.entrySet())
            if (entry.getValue().equals(pet)) return entry.getKey();
        return -1;
    }


    public PetServiceImpl(PetBaseRepository petBaseRepository) {
        this.petBaseRepository = petBaseRepository;
        this.cat = cat;
        this.dog = dog;
    }

    @Override
    public int getId() {
        return 0;
    }



    @Override
    public Pet getPetById(int recordId) {
        return null;
    }

    @Override
    public Pet getPetByIdInt(int recordId) {
        return null;
    }

    @Override
    public List<Pet> getAllPets() {
        return null;
    }


    @Override
    public void deleteAllFromPet() {
    }



    ///**************************** методы репозитория **************

   public Pet getPetById (long petId){
      return catRepository.getById(petId);
   }

    //************* КОШКИ: ************
    public void saveCat(Cat cat) {
        catRepository.save(cat);
    }

    public Cat getCatById (long petId){
        return catRepository.getById(petId);
    }


    // ******************* СОБАКИ ****************
    public void saveDog(Dog dog) {
        dogRepository.save(dog);
    }

    public Dog getDogById (long petId){
        return dogRepository.getById(petId);
    }
}
