package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.SuperemeAppealReporter.api.io.entity.PostEntity;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Integer>{

	@Query(value = "select p.id, p.body,p.heading, p.linkedDocId from PostEntity p where p.active = 1  order by p.createdDate desc")
	public Page<Object> getPostList(Pageable page);
}
