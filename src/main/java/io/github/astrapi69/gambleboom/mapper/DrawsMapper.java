package io.github.astrapi69.gambleboom.mapper;

import io.github.astrapi69.gambleboom.viewmodel.Draw;
import io.github.astrapi69.gambleboom.jpa.entity.Draws;
import org.springframework.stereotype.Component;

import de.alpharogroup.bean.mapper.AbstractGenericMapper;

@Component
public class DrawsMapper extends AbstractGenericMapper<Draws, Draw>
{
}
