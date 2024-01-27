package com.SecondPersonalProject.Stones.mappers;

import org.mapstruct.Mapper;

import com.SecondPersonalProject.Stones.dtos.StoneDTO;
import com.SecondPersonalProject.Stones.models.Stone;

@Mapper
public interface StoneMapper {
	StoneDTO stoneToStoneDTO(Stone stone);
}
