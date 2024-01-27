package com.SecondPersonalProject.Stones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SecondPersonalProject.Stones.models.Stone;

@Repository
public interface StoneRepository extends JpaRepository<Stone, Long>{

}
