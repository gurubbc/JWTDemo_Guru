package com.trivium.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trivium.demo.entity.TriviumUser1;

public interface TriviumUser1UserRepo extends JpaRepository<TriviumUser1, Long>{

	
	Optional<TriviumUser1> findByUserName(String triviumUserRepo);
}
