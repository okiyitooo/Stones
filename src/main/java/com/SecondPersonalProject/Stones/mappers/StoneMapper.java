package com.SecondPersonalProject.Stones.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.SecondPersonalProject.Stones.dtos.StoneDTO;
import com.SecondPersonalProject.Stones.models.Stone;

@Mapper
public interface StoneMapper {
	
	StoneMapper INSTANCE = Mappers.getMapper(StoneMapper.class);
	
	@Mapping(target = "")
	StoneDTO stoneToStoneDTO(Stone stone);
}
