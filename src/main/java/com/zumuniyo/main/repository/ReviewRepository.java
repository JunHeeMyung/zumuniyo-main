package com.zumuniyo.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.zumuniyo.main.dto.ReviewDTO;

public interface ReviewRepository extends CrudRepository<ReviewDTO, Long>{

}
