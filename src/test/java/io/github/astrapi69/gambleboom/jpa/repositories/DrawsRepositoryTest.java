package io.github.astrapi69.gambleboom.jpa.repositories;

import de.alpharogroup.collections.set.SetFactory;
import de.alpharogroup.jdbc.ConnectionsExtensions;
import de.alpharogroup.jdbc.JdbcUrlBean;
import de.alpharogroup.jdbc.PostgreSQLConnectionsExtensions;
import io.github.astrapi69.gambleboom.AbstractIntegrationTest;
import io.github.astrapi69.gambleboom.config.ApplicationConfiguration;
import io.github.astrapi69.gambleboom.config.ApplicationProperties;
import io.github.astrapi69.gambleboom.config.SwaggerConfiguration;
import io.github.astrapi69.gambleboom.jpa.entities.Draws;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
	@Autowired TestEntityManager entityManagerDecorator;

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


	@Test
	public void testVerify()
	{
		// new scenario...
		// draw the first object
		Draws saved = drawsRepository.saveAndFlush(Draws.builder().drawnDate(LocalDateTime.now())
			.lotteryNumbers(SetFactory.newHashSet(2, 5, 11, 23, 25, 45)).build());
		String signature = saved.getSignature();
		assertThat(saved).isNotNull();
		assertThat(signature).isNotNull();

		Draws one = drawsRepository.getOne(saved.getId());
		assertThat(one).isNotNull();
	}

}
