package com.ward_n6.repository;

import com.ward_n6.entity.owners.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface OwnerRepository extends JpaRepository<Owner, Long> {
//    Owner addOwner(Owner owner) throws PutToMapException;

//    Owner getOwnerById(int recordId);
//
//    List<Owner> getAllOwners();
//
////    Owner editOwnerById(int recordId, Owner owner) throws EditMapException;
//
//    void deleteAll();
//
//    boolean deleteOwnerById(int recordId) throws DeleteFromMapException;
//
//    boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException;
//
//    int idOwnerByValue(Owner owner);
//
//    int getId();

//    Owner addOwnerToDB(Owner owner);
    @Override
    List<Owner> findAll();



}
