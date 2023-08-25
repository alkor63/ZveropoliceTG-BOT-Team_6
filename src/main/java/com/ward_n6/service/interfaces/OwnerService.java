package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.Owner;


import java.util.List;

public interface OwnerService {
    int getId();


//     добавление в БД
    Owner addOwnerToDB(Owner owner);

    Owner getOwnerById(int recordId);

    List<Owner> getAllOwners();



    void deleteAllFromOwner();


    int idOwnerByValue(Owner owner);

}
