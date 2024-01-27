package com.SecondPersonalProject.Stones.servicesAndImpls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.StoneCreationException;
import com.SecondPersonalProject.Stones.exceptions.StoneNotFoundException;
import com.SecondPersonalProject.Stones.models.Stone;

public interface StoneService {
//	Stone createStone( @Validated StoneDto stoneDTO) throws StoneCreationException;

	Stone createStone(@Validated Stone stone) throws StoneCreationException;
	
	Page<Stone> getAllStones(Pageable pageable) throws EmptyPageException;

	Stone whatIsThis(long stoneId) throws StoneNotFoundException;
}
