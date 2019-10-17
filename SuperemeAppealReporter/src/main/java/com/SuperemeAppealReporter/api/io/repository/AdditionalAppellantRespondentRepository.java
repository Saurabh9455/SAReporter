package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.AdditionalAppellantRespondentEntity;

@Repository
public interface AdditionalAppellantRespondentRepository extends PagingAndSortingRepository<AdditionalAppellantRespondentEntity, Integer> {

}
