package com.cielicki.dominik.allergyapprestapi.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipAllergenList;
import com.cielicki.dominik.allergyapprestapi.db.repository.VoivodeshipAllergenService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

/**
 * Endpoint służący do zapisywania i pobierania allergenów w województwie.
 */
@Component
@RestController
@RequestMapping("/voivodeshipAllergen")
public class VoivodeshipAllergenController {
	class DummyDate {
		Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
	
	@Autowired
	VoivodeshipAllergenService voivodeshipAllergenService;

	/**
	 * Zapisuje zawartość alergenów w województwie do bazy.
	 * 
	 * @param voivodeshipAllergen Obiekt alergenu w województwie.
	 */
	@PostMapping(path="/addVoivodeshipAllergen", consumes="application/json", produces="application/json")
	public void addVoivodeshipAllergen(@RequestBody VoivodeshipAllergen voivodeshipAllergen) {
		voivodeshipAllergenService.save(voivodeshipAllergen);
	}
	
	/**
	 * Zwraca listę wszystkich alergenów we wszystkich województwach.
	 * 
	 * @return Zwraca listę województw.
	 */
	@GetMapping(path="/getVoivodeshipAllergens", produces = "application/json")
	public VoivodeshipAllergenList getVoivodeshipAllergen() {
		return voivodeshipAllergenService.findAll();
	}
	
	/**
	 * Zwraca listę alergenów w województwie.
	 * 
	 * @param voivodeship Obiekt województwa.
	 * 
	 * @return Zwraca alergenów w województwie.
	 */
	@PostMapping(path="/getVoivodeshipAllergensByVoivodeship", consumes = "application/json",produces = "application/json")
	public VoivodeshipAllergenList getVoivodeshipAllergenByVoivodenship(@RequestBody(required = false) Voivodeship voivodeship) {
		return voivodeshipAllergenService.findAllByVoivodenship(voivodeship);
	}
	
	/**
	 * Zwraca listę alergenów w województwie.
	 * 
	 * @param voivodeship Obiekt województwa.
	 * 
	 * @return Zwraca alergenów w województwie.
	 */
	@PostMapping(path="/getVoivodeshipAllergensByVoivodeshipAndDate", consumes = "application/json",produces = "application/json")
	public VoivodeshipAllergenList getVoivodeshipAllergenByVoivodenship(@RequestBody(required = false) String requestBody) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		JsonObject json = gson.fromJson(requestBody, JsonObject.class);
		Voivodeship voivodeship = gson.fromJson(json.get("voivodeship"), Voivodeship.class);
		Date date = gson.fromJson(json.get("date"), DummyDate.class).getDate();
		
		return voivodeshipAllergenService.findAllByVoivodenshipAndDate(voivodeship, date);
	}
}
