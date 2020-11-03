package io.github.astrapi69.gambleboom.jpa.repositories;

import io.github.astrapi69.gambleboom.jpa.entities.Draws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository public interface DrawnNumbersRepository extends JpaRepository<Draws, UUID>
{
}
