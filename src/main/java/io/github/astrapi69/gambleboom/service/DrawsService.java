package io.github.astrapi69.gambleboom.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import de.alpharogroup.sign.JsonVerifier;
import io.github.astrapi69.gambleboom.jpa.entity.Draws;
import io.github.astrapi69.gambleboom.jpa.repository.DrawsRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DrawsService
{
	DrawsRepository drawsRepository;

	JsonVerifier<Draws> drawsJsonVerifier;

	public Draws getById(UUID id)
	{
		Draws draws = null;
		Optional<Draws> optionalDraws = drawsRepository.findById(id);
		if (optionalDraws.isPresent())
		{
			draws = optionalDraws.get();
			boolean valid = drawsJsonVerifier.verify(draws, draws.getSignature());
			if (!valid)
			{
				throw new RuntimeException("Draw manipulated");
			}
		}
		return draws;
	}
}
