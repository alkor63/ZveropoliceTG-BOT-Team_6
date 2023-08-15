package com.ward_n6.repository.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.repository.OwnerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    private Map<Integer, Owner> ownerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public Owner addOwner(Owner owner) {
        if (owner.equals(null)) throw new NullPointerException("ОШИБКА при попытке добавить owner=Null в МАПу ownerMap");
        ownerMap.putIfAbsent(mapId++, owner);
        return owner;
    }
    @Override
    public Owner addOwnerToDB(Owner owner) {
        try {
            // Создаем подключение к базе данных
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:5432/postgres",
                    "postgres", "Sql2374");

            // Создаем запрос на добавление записи в таблицу:
            String query = "INSERT INTO owner (first_Name, last_Name, owner_Phone) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, owner.getFirstName());
            statement.setString(2, owner.getLastName());
            statement.setString(3, owner.getPhoneNumber());

            // Выполняем запрос и закрываем соединение:
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (
                SQLException e) {
            System.out.println("Ошибка при добавлении записи в базу данных: " + e.getMessage());
        }
        return owner;
    }
    @Override
    public Owner getOwnerById(int recordId) {
        return ownerMap.get(recordId);
    }

    @Override
    public List<Owner> getAllOwners() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public Owner editOwnerById(int recordId, Owner owner) throws EditMapException {
        if (ownerMap.containsKey(recordId)) {
            ownerMap.put(recordId, owner);
            if (!ownerMap.get(recordId).equals(owner)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись о госте "
                        + owner.getFirstName() + " " + owner.getLastName() + " в МАПе ownerMap под id=" + recordId);
            }
            return null;
        }
        return ownerMap.get(recordId);
    }

    @Override
    public void deleteAllFromOwner() {
        ownerMap.clear();
    }

    @Override
    public boolean deleteOwnerById(int recordId) throws DeleteFromMapException {
        if (ownerMap.containsKey(recordId)) {
            ownerMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о госте в МАПе ownerMap под id=" + recordId);
    }

    @Override
    public boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException {
        if (ownerMap.containsValue(owner)) {
            ownerMap.values().remove(owner);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о госте "
                + owner.getFirstName() + " " + owner.getLastName() + " из МАПы ownerMap");
    }

    @Override
    public int idOwnerByValue(Owner owner) {
        for (Map.Entry<Integer, Owner> entry : ownerMap.entrySet())
            if (entry.getValue().equals(owner)) return entry.getKey();
        return -1;
    }
    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public List<Owner> findAll() {
        return null;
    }

    @Override
    public List<Owner> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Owner> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Owner> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Owner entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Owner> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Owner> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Owner> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Owner> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Owner> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param aLong
     * @deprecated
     */
    @Override
    public Owner getOne(Long aLong) {
        return null;
    }

    @Override
    public Owner getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Owner> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Owner> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Owner> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Owner> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Owner> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Owner, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
