package io.github.astrapi69.gambleboom.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.astrapi69.gambleboom.jpa.entity.Draws;

@Repository
public interface DrawsRepository extends JpaRepository<Draws, UUID>
{
}
