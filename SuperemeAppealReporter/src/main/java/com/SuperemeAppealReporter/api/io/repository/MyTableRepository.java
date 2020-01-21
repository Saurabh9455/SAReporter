package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.MyTable;

@Repository
public interface MyTableRepository extends CrudRepository<MyTable, Long> {

    @Procedure(name = "updateUserPlanStatus")
    void updateUserPlanStatus();


 }