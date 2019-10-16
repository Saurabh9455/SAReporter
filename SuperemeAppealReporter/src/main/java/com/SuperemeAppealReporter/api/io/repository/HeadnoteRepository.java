package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;

@Repository
public interface HeadnoteRepository extends PagingAndSortingRepository<HeadnoteEntity, Integer> {

}
