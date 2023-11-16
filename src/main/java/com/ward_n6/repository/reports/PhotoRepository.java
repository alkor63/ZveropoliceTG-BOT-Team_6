package com.ward_n6.repository.reports;

import com.ward_n6.entity.reports.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PhotoRepository extends JpaRepository <Photo, Long> {
}
