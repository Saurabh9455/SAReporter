package com.SuperemeAppealReporter.api.io.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

@Entity

@Table(name = "MYTABLE")

@NamedStoredProcedureQueries({

   @NamedStoredProcedureQuery(name = "updateUserPlanStatus", 

                              procedureName = "updateUserPlanStatus" )

                             
})

public class MyTable extends BaseEntity implements Serializable {

}