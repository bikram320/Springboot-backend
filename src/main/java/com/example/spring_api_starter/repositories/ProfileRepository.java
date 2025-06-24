package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
