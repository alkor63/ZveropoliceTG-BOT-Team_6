package com.ward_n6.service;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.CatsCrud;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CatService {

    @Resource
    private CatsCrud catsCrud;


    public void addCat(PetsSex petsSex, Cat_2 cat_2) {
        cat_2.setPetsSex(petsSex);
        catsCrud.save(cat_2);
    }

    public Cat_2 findCat(long id) {
        Optional<Cat_2> byId = catsCrud.findById(id);
        return byId.orElse(null);
    }

    public String deleteCat(long id) {
        try {
            Cat_2 cat = findCat(id);
            catsCrud.deleteById(id);
            return cat.getPetName() + " удалена";
        } catch (EmptyResultDataAccessException e) {
            return "Такая кошка была удалена ранее или вы ошиблись с поиском.";
        }
    }


    public String allCats() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cat_2 cat2 : catsCrud.findAll()) {
            stringBuilder.append("\n").append(cat2);
        }
        return stringBuilder.toString();
    }

    public Cat_2 change(long id, Cat_2 cat_2, PetsSex petsSex) {
        cat_2.setId(id);
        cat_2.setPetsSex(petsSex);
        return catsCrud.save(cat_2);
    }
}

