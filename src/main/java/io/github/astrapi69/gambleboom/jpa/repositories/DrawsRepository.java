package io.github.astrapi69.gambleboom.jpa.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.astrapi69.gambleboom.jpa.entities.Draws;

@Repository
public interface DrawsRepository extends JpaRepository<Draws, UUID>
{
}
