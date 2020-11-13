package io.github.astrapi69.gambleboom.jpa.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.alpharogroup.collections.set.SetFactory;
import io.github.astrapi69.gambleboom.AbstractIntegrationTest;
import io.github.astrapi69.gambleboom.config.ApplicationConfiguration;
import io.github.astrapi69.gambleboom.config.ApplicationProperties;
import io.github.astrapi69.gambleboom.jpa.entities.Draws;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ ApplicationConfiguration.class })
@EnableConfigurationProperties({ ApplicationProperties.class })
public class DrawsRepositoryTest extends AbstractIntegrationTest
{
	@Autowired
	private DrawsRepository drawsRepository;

	@Test
	public void testSave() {
		// new scenario...
		// draw the first object
		Draws saved = drawsRepository.saveAndFlush(Draws.builder().drawnDate(LocalDateTime.now())
			.lotteryNumbers(SetFactory.newHashSet(2, 5, 11, 23, 25, 45)).build());
		String signature = saved.getSignature();
		assertThat(saved).isNotNull();
		assertThat(signature).isNotNull();
		// new scenario...
		// draw the second object
		Draws saved2 = drawsRepository.saveAndFlush(Draws.builder().drawnDate(LocalDateTime.now())
			.lotteryNumbers(SetFactory.newHashSet(1, 6, 17, 23, 26, 47)).build());
		String newSignature = saved2.getSignature();
		assertThat(saved2).isNotNull();
		assertThat(signature).isNotEqualTo(newSignature);
		// change draw date of first draw
		saved.setDrawnDate(LocalDateTime.now());
		saved = drawsRepository.saveAndFlush(saved);
		newSignature = saved.getSignature();
		assertThat(signature).isNotEqualTo(newSignature);
	}

}
