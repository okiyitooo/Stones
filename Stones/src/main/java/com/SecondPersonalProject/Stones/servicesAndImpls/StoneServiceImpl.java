package com.SecondPersonalProject.Stones.servicesAndImpls;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.StoneCreationException;
import com.SecondPersonalProject.Stones.exceptions.StoneNotFoundException;
import com.SecondPersonalProject.Stones.models.Stone;
import com.SecondPersonalProject.Stones.repositories.StoneRepository;

@Service
public class StoneServiceImpl implements StoneService {

	@Autowired
	StoneRepository stoneRepository; 

	@Override
	public Stone createStone(Stone stone) throws StoneCreationException {

        // Persist the entity using Spring Data JPA
        try {
            stone = stoneRepository.save(stone);
        } catch (Exception e) {
            throw new StoneCreationException("Failed to create stone", e);
        }

        return stone;
	}

	@Override
    public Page<Stone> getAllStones(Pageable pageable) throws EmptyPageException {
        Page<Stone> stonesPage = stoneRepository.findAll(pageable);

        if (stonesPage.isEmpty()) {
            throw new EmptyPageException("No stones found");
        }

        return stonesPage;
    }
	
	@Override
	public Stone whatIsThis(long stoneId) throws StoneNotFoundException {
		Stone stone = stoneRepository.findById(stoneId).orElseThrow(() -> new StoneNotFoundException(String.format("Stone with ID %d not found", stoneId)));
		return stone;
	}
}
