package com.zumuniyo.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.zumuniyo.main.dto.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, Long>{


}