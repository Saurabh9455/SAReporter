package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.CrudRepository;

import com.SuperemeAppealReporter.api.io.entity.DocIdDummyEntity;

public interface DummyDocIdRepository extends CrudRepository<DocIdDummyEntity, Integer>{

	public DocIdDummyEntity findByDocId(String docId);
}
