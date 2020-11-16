package io.github.astrapi69.gambleboom.jpa.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;

import io.github.astrapi69.gambleboom.jpa.repository.DrawsRepository;
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
import io.github.astrapi69.gambleboom.jpa.entity.Draws;
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
		Draws firstDraw = drawsRepository.saveAndFlush(
			Draws.builder()
				.drawnDate(LocalDateTime.now())
			.lotteryNumbers(
				SetFactory.newHashSet(2, 5, 11, 23, 25, 45))
				.build());
		String signature = firstDraw.getSignature();
		assertThat(firstDraw).isNotNull();
		assertThat(signature).isNotNull();
		// new scenario...
		// draw the second object
		Draws secondDraw = drawsRepository.saveAndFlush(
			Draws.builder()
				.drawnDate(LocalDateTime.now())
			.lotteryNumbers(
				SetFactory.newHashSet(1, 6, 17, 23, 26, 47))
				.build());
		String newSignature = secondDraw.getSignature();
		assertThat(secondDraw).isNotNull();
		assertThat(signature).isNotEqualTo(newSignature);
		// change draw date of first draw
		firstDraw.setDrawnDate(LocalDateTime.now());
		firstDraw = drawsRepository.saveAndFlush(firstDraw);
		newSignature = firstDraw.getSignature();
		assertThat(signature).isNotEqualTo(newSignature);
	}

}
