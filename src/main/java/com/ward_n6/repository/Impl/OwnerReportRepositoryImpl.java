package com.ward_n6.repository.Impl;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.InvalidRequestException;
import com.ward_n6.repository.OwnerReportRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository

//public class OwnerReportRepositoryImpl implements OwnerReportRepository {

//
//    @Override
//    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
//        if (ownerReport.equals(null)) throw new NullPointerException(
//                "ОШИБКА при попытке добавить ownerReport=Null в МАПу ownerReportMap");
//        return save(ownerReport);
//    }
//
//    @Override
//    public Optional<OwnerReport> getOwnerReportById(Integer recordId) {
//        return findById(recordId);
//    }
//
//    @Override
//    public List<OwnerReport> getAllOwnerReports() {
//        return findAll();
//    }
//
//    @Override
//    public OwnerReport editOwnerReportById(Integer recordId, OwnerReport ownerReport) {
//        if (findById(recordId).isPresent()) return save(ownerReport);
//           else throw new InvalidRequestException("ОШИБКА при попытке изменить запись отчета о животном c petId = "
//                    +ownerReport.getPetId()+" в МАПе ownerReportMap под id="+recordId);
//    }
//
//
//    @Override
//    public boolean deleteOwnerReportById(Integer recordId) {
//        if (findById(recordId).isPresent()) {
//             deleteById(recordId);
//            return true;
//        }
//        throw new InvalidRequestException(
//                "ОШИБКА при попытке удалить запись отчета о животном в МАПе ownerReportMap под id="+recordId);
//    }
//
//    @Override
//    public List<OwnerReport> findAllByOwnerIdIsNotNull() {
//return new ArrayList<>(findAll());
//    }


//    @Override
//    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
//
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();) {
//            Transaction transaction = session.beginTransaction();
//            session.save(ownerReport);
//            transaction.commit();
//        }
//            return ownerReport;
//        }
//
//    @Override
//    public OwnerReport getOwnerReportById(Integer id) {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            return session.get(OwnerReport.class, id);
//        }
//    }
//
//    @Override
//    public List<OwnerReport> getAllOwnerReport() {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            return session.createQuery("FROM OwnerReport ").list();
//        }
//    }
//
//    @Override
//    public OwnerReport updateOwnerReport(Integer recordId, OwnerReport ownerReport) {
//
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.update(ownerReport);
//            transaction.commit();
//        }
//        return ownerReport;
//    }
//    @Override
//    public void deleteOwnerReport(OwnerReport ownerReport) {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.delete(ownerReport);
//            transaction.commit();
//        }
//    }
//    @Override
//    public boolean deleteOwnerReportById(Integer id) {
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.delete(String.valueOf(OwnerReport.class), id);
//            transaction.commit();
//        }
//        return false;
//    }
//}
