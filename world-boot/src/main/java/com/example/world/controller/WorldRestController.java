package com.example.world.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.world.dao.inmemory.InMemoryWorldDao;
import com.example.world.entity.Country;

@RestController
@RequestMapping
@CrossOrigin("*")
public class WorldRestController {
	private InMemoryWorldDao inMemoryWorldDao;

	public WorldRestController(InMemoryWorldDao inMemoryWorldDao) {
		super();
		this.inMemoryWorldDao = inMemoryWorldDao;
	}

	// http://localhost:8200/world/api/v1/continents
	@GetMapping("continents")
	public Collection<String> getContinents() {
		return inMemoryWorldDao.getAllContinents();
	}

	// http://localhost:8200/world/api/v1/countries?continent=Africa
	@GetMapping("countries")
	public Collection<Country> getCountries(@RequestParam String continent) {
		return inMemoryWorldDao.findCountriesByContinent(continent);
	}
}
