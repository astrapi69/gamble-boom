package io.github.astrapi69.gambleboom.controller;

import java.time.LocalDateTime;
import java.util.Set;

import io.github.astrapi69.gambleboom.config.ApplicationConfiguration;
import io.github.astrapi69.gambleboom.mapper.DrawsMapper;
import io.github.astrapi69.gambleboom.viewmodel.Draw;
import io.github.astrapi69.gambleboom.jpa.entity.Draws;
import io.github.astrapi69.gambleboom.service.DrawsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.alpharogroup.lottery.drawing.DrawnLotteryNumbersFactory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + DrawsController.REST_PATH)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DrawsController
{
	public static final String REST_PATH = "/draws";

	DrawsService drawsService;

	DrawsMapper drawsMapper;

	@RequestMapping(method = RequestMethod.POST)
	public Draw newDraw() {
		Set<Integer> lotteryNumbers = DrawnLotteryNumbersFactory.newRandomDrawnLotteryNumbers()
			.getLotteryNumbers();
		Draws save = drawsService
			.save(Draws.builder().drawnDate(LocalDateTime.now()).lotteryNumbers(lotteryNumbers).build());
		return drawsMapper.toDto(save);
	}
}
