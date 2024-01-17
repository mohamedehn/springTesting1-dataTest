package com.testSpring1.questDataTest.repository;

import com.testSpring1.questDataTest.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireRepository extends JpaRepository<Fire, Long> {
}
