package com.ward_n6.repository;

import com.ward_n6.entity.BotMessaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
@EnableJpaRepositories
public interface BotMessagingRepository extends JpaRepository<BotMessaging, Long> {
    List<BotMessaging> textingWithOwner = new ArrayList<>();
    @Override
    List<BotMessaging> findAll();


}
