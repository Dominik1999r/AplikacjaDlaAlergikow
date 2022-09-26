package com.cielicki.dominik.allergyapprestapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.cielicki.dominik.allergyapprestapi.db.Allergen;
import com.cielicki.dominik.allergyapprestapi.db.Chat;
import com.cielicki.dominik.allergyapprestapi.db.Medicine;
import com.cielicki.dominik.allergyapprestapi.db.MedicineComment;
import com.cielicki.dominik.allergyapprestapi.db.MedicineCommentId;
import com.cielicki.dominik.allergyapprestapi.db.Messages;
import com.cielicki.dominik.allergyapprestapi.db.Question;
import com.cielicki.dominik.allergyapprestapi.db.User;
import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergenId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FillDatabase {
	public static void main(String[] args) throws ClientProtocolException, IOException {
//		addUser();
//		addGlobalUser();
//		addSecondUser();
//		addMedicines();
//		addMedicineComments();
//		addVoivodeship();
//		addAllergen();
//		addVoivodeshipAllergen();
//		addVoivodeshipAllergen2();
//		addVoivodeshipAllergen3();
//		addVoivodeshipAllergen4();
//		addVoivodeshipAllergen5();
//		addQuestions();
//		addChat();
//		addMessage();
//		addGlobalChat();
//		testChat();
//		testGlobalChat();
//		testGlobalChatSecondUser();
		addQuestion();
	}
	
	public static void addUser() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/addUser");
		User user = new User();
		user.setEmail("test@test.pl");
		user.setId(new Long(100));
		user.setLastName("Testowski");
		user.setName("Tester");
		user.setPassword("testPassword");
		user.setSalt("1234");
		user.setUsername("test_testowski_login");
		
		StringEntity stringEntity = new StringEntity(new Gson().toJson(user), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addGlobalUser() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/addUser");
		httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
		User user = User.GLOBAL_CHAT_USER;
		user.setEmail("global@global.pl");
		user.setLastName("Globalski");
		user.setName("Global");
		user.setPassword("global");
		user.setSalt("global");
		user.setUsername("global_user");
		
		StringEntity stringEntity = new StringEntity(new Gson().toJson(user), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addSecondUser() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/user/addUser");
		User user = new User();
		user.setEmail("drugi@test.pl");
		user.setId(new Long(200));
		user.setLastName("Testowski");
		user.setName("Drugi");
		user.setPassword("testPassword2");
		user.setSalt("2345");
		user.setUsername("drugi_testowski_login");
		
		StringEntity stringEntity = new StringEntity(new Gson().toJson(user), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addMedicines() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/medicine/addMedicine");
		Medicine medicine = new Medicine();
//		medicine.setId(new Long(1));
		medicine.setName("Test medicine 1");
		medicine.setDescription("Testowy lek");
		medicine.setPriceLow(new BigDecimal("5"));
		medicine.setPriceHigh(new BigDecimal("55"));
		
		StringEntity stringEntity = new StringEntity(new Gson().toJson(medicine), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
		
		httpPost = new HttpPost("http://localhost:8080/medicine/addMedicine");
		
		medicine = new Medicine();
//		medicine.setId(new Long(2));
		medicine.setName("Test medicine 2");
		medicine.setDescription("Testowy lek dwa");
		medicine.setPriceLow(new BigDecimal("15"));
		medicine.setPriceHigh(new BigDecimal("535"));
		
		stringEntity = new StringEntity(new Gson().toJson(medicine), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addMedicineComments() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/medicineComment/addMedicineComment");
		Medicine medicine = new Medicine();
		medicine.setId(new Long(3));
		medicine.setName("Test medicine 1");
		medicine.setDescription("Testowy lek");
		medicine.setPriceLow(new BigDecimal("5"));
		medicine.setPriceHigh(new BigDecimal("55"));
		
		User user = new User();
		user.setEmail("test@test.pl");
		user.setId(new Long(100));
		user.setLastName("Testowski");
		user.setName("Tester");
		user.setPassword("testPassword");
		user.setSalt("1234");
		user.setUsername("test_testowski_login");
		user.setId(new Long(1));
		
		MedicineComment medicineComment = new MedicineComment();
		medicineComment.setComment("Komentarz leku Test medicine 1");
		medicineComment.setRating(new BigDecimal(4.71));
		
		MedicineCommentId id = new MedicineCommentId(user, medicine);
		id.setDate(new Date());
		medicineComment.setId(id);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		StringEntity stringEntity = new StringEntity(gson.toJson(medicineComment), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addVoivodeship() throws ClientProtocolException, IOException {
		List<Voivodeship> list = new ArrayList<Voivodeship>();
		
		Voivodeship DOLNOSLASKIE = new Voivodeship();
		DOLNOSLASKIE.setName("Dolnośląskie");
		DOLNOSLASKIE.setId(1L);
		list.add(DOLNOSLASKIE);
		
		Voivodeship KUJAWSKO_POMORSKIE = new Voivodeship();
		KUJAWSKO_POMORSKIE.setName("Kujawsko-Pomorskie");
		KUJAWSKO_POMORSKIE.setId(2L);
		list.add(KUJAWSKO_POMORSKIE);
		
		Voivodeship LUBELSKIE = new Voivodeship();
		LUBELSKIE.setName("Lubelskie");
		LUBELSKIE.setId(3L);
		list.add(LUBELSKIE);
		
		Voivodeship LUBUSKIE = new Voivodeship();
		LUBUSKIE.setName("Lubuskie");
		LUBUSKIE.setId(4L);
		list.add(LUBUSKIE);
		
		Voivodeship LODZKIE = new Voivodeship();
		LODZKIE.setName("Łódzkie");
		LODZKIE.setId(5L);
		list.add(LODZKIE);
		
		Voivodeship MALOPOLSKIE = new Voivodeship();
		MALOPOLSKIE.setName("Małopolskie");
		MALOPOLSKIE.setId(6L);
		list.add(MALOPOLSKIE);
		
		Voivodeship MAZOWIECKIE = new Voivodeship();
		MAZOWIECKIE.setName("Mazowieckie");
		MAZOWIECKIE.setId(7L);
		list.add(MAZOWIECKIE);
		
		Voivodeship OPOLSKIE = new Voivodeship();
		OPOLSKIE.setName("Opolskie");
		OPOLSKIE.setId(8L);
		list.add(OPOLSKIE);
		
		Voivodeship PODKARPACKIE = new Voivodeship();
		PODKARPACKIE.setName("Podkarpackie");
		PODKARPACKIE.setId(9L);
		list.add(PODKARPACKIE);
		
		Voivodeship PODLASKIE = new Voivodeship();
		PODLASKIE.setName("Podlaskie");
		PODLASKIE.setId(10L);
		list.add(PODLASKIE);
		
		Voivodeship POMORSKIE = new Voivodeship();
		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(11L);
		list.add(POMORSKIE);
		
		Voivodeship SLASKIE = new Voivodeship();
		SLASKIE.setName("Śląskie");
		SLASKIE.setId(12L);
		list.add(SLASKIE);
		
		Voivodeship SWIETOKRZYSKIE = new Voivodeship();
		SWIETOKRZYSKIE.setName("Świętokrzyskie");
		SWIETOKRZYSKIE.setId(13L);
		list.add(SWIETOKRZYSKIE);
		
		Voivodeship WARMINSKO_MAZURSKIE = new Voivodeship();
		WARMINSKO_MAZURSKIE.setName("Warmińsko-Mazurskie");
		WARMINSKO_MAZURSKIE.setId(14L);
		list.add(WARMINSKO_MAZURSKIE);
		
		Voivodeship WIELKOPOLSKIE = new Voivodeship();
		WIELKOPOLSKIE.setName("Wielkopolskie");
		WIELKOPOLSKIE.setId(15L);
		list.add(WIELKOPOLSKIE);
		
		Voivodeship ZACHODNIOPOMORSKIE = new Voivodeship();
		ZACHODNIOPOMORSKIE.setName("Zachodniopomorskie");
		ZACHODNIOPOMORSKIE.setId(16L);
		list.add(ZACHODNIOPOMORSKIE);

		Long i = 1L;
		
		for(Voivodeship voivodeship: list) {
			voivodeship.setId(i);
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeship/addVoivodeship");
			StringEntity stringEntity = new StringEntity(new Gson().toJson(voivodeship), ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			httpClient.execute(httpPost);			
			i++;
		}
	}
	
	public static void addAllergen() throws ClientProtocolException, IOException {
		Allergen allergen = new Allergen();
		allergen.setId(3L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/allergen/addAllergen");
		StringEntity stringEntity = new StringEntity(new Gson().toJson(allergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addVoivodeshipAllergen() throws ClientProtocolException, IOException {
		VoivodeshipAllergen voivodeshipAllergen = new VoivodeshipAllergen();
		voivodeshipAllergen.setValue(new BigDecimal(12.34));
		
		Allergen allergen = new Allergen();
		allergen.setId(26L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		Voivodeship POMORSKIE = new Voivodeship();
		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(11L);
		
		VoivodeshipAllergenId voivodeshipAllergenId = new VoivodeshipAllergenId();
		voivodeshipAllergenId.setAllergen(allergen);
		voivodeshipAllergenId.setStartDate(new Date());
		voivodeshipAllergenId.setVoivodeship(POMORSKIE);
		
		voivodeshipAllergen.setId(voivodeshipAllergenId);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeshipAllergen/addVoivodeshipAllergen");
		StringEntity stringEntity = new StringEntity(gson.toJson(voivodeshipAllergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addVoivodeshipAllergen2() throws ClientProtocolException, IOException {
		VoivodeshipAllergen voivodeshipAllergen = new VoivodeshipAllergen();
		voivodeshipAllergen.setValue(new BigDecimal(50));
		
		Allergen allergen = new Allergen();
		allergen.setId(26L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		Voivodeship POMORSKIE = new Voivodeship();
		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(11L);
		
		VoivodeshipAllergenId voivodeshipAllergenId = new VoivodeshipAllergenId();
		voivodeshipAllergenId.setAllergen(allergen);
		voivodeshipAllergenId.setStartDate(new Date());
		voivodeshipAllergenId.setVoivodeship(POMORSKIE);
		
		voivodeshipAllergen.setId(voivodeshipAllergenId);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeshipAllergen/addVoivodeshipAllergen");
		StringEntity stringEntity = new StringEntity(gson.toJson(voivodeshipAllergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addVoivodeshipAllergen3() throws ClientProtocolException, IOException {
		VoivodeshipAllergen voivodeshipAllergen = new VoivodeshipAllergen();
		voivodeshipAllergen.setValue(new BigDecimal(30));
		
		Allergen allergen = new Allergen();
		allergen.setId(26L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		Voivodeship POMORSKIE = new Voivodeship();
		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(11L);
		
		VoivodeshipAllergenId voivodeshipAllergenId = new VoivodeshipAllergenId();
		voivodeshipAllergenId.setAllergen(allergen);
		voivodeshipAllergenId.setStartDate(new Date());
		voivodeshipAllergenId.setVoivodeship(POMORSKIE);
		
		voivodeshipAllergen.setId(voivodeshipAllergenId);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeshipAllergen/addVoivodeshipAllergen");
		StringEntity stringEntity = new StringEntity(gson.toJson(voivodeshipAllergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addVoivodeshipAllergen4() throws ClientProtocolException, IOException {
		VoivodeshipAllergen voivodeshipAllergen = new VoivodeshipAllergen();
		voivodeshipAllergen.setValue(new BigDecimal(40));
		
		Allergen allergen = new Allergen();
		allergen.setId(26L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		Voivodeship POMORSKIE = new Voivodeship();
		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(16L);
		
		VoivodeshipAllergenId voivodeshipAllergenId = new VoivodeshipAllergenId();
		voivodeshipAllergenId.setAllergen(allergen);
		voivodeshipAllergenId.setStartDate(new Date());
		voivodeshipAllergenId.setVoivodeship(POMORSKIE);
		
		voivodeshipAllergen.setId(voivodeshipAllergenId);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeshipAllergen/addVoivodeshipAllergen");
		StringEntity stringEntity = new StringEntity(gson.toJson(voivodeshipAllergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addVoivodeshipAllergen5() throws ClientProtocolException, IOException {
		VoivodeshipAllergen voivodeshipAllergen = new VoivodeshipAllergen();
		voivodeshipAllergen.setValue(new BigDecimal(20));
		
		Allergen allergen = new Allergen();
		allergen.setId(26L);
		allergen.setName("Allergen test");
		allergen.setMinValue(new BigDecimal(10));
		allergen.setMaxValue(new BigDecimal(50));
		allergen.setDescription("Opis allergenu");
		
		Voivodeship POMORSKIE = new Voivodeship();
//		POMORSKIE.setName("Pomorskie");
		POMORSKIE.setId(16L);
		
		VoivodeshipAllergenId voivodeshipAllergenId = new VoivodeshipAllergenId();
		voivodeshipAllergenId.setAllergen(allergen);
		voivodeshipAllergenId.setStartDate(new Date());
		voivodeshipAllergenId.setVoivodeship(POMORSKIE);
		
		voivodeshipAllergen.setId(voivodeshipAllergenId);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/voivodeshipAllergen/addVoivodeshipAllergen");
		StringEntity stringEntity = new StringEntity(gson.toJson(voivodeshipAllergen), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addQuestions() throws ClientProtocolException, IOException {
		Question question = new Question();
		question.setSubject("Jakie są alergeny?");
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/question/addQuestion");
		StringEntity stringEntity = new StringEntity(new Gson().toJson(question), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);	
	}
	
	public static void addChat() throws ClientProtocolException, IOException {
		Chat chat = new Chat();
		chat.setLastMessage(null);
		chat.setSubject("Test chatu");
		User user = new User();
		user.setId(1L);
		chat.setUser(user);
		
		User receiver = new User();
		receiver.setId(30L);
		chat.setUser2(receiver);
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/chat/addChat");
		StringEntity stringEntity = new StringEntity(new Gson().toJson(chat), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addGlobalChat() throws ClientProtocolException, IOException {
		Chat chat = new Chat();
		chat.setLastMessage(null);
		chat.setSubject("Test globalny");
		User user = new User();
		user.setId(1L);
		chat.setUser(user);
		
		chat.setUser2(User.GLOBAL_CHAT_USER);
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/chat/addChat");
		httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
		StringEntity stringEntity = new StringEntity(new Gson().toJson(chat), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void addMessage() throws ClientProtocolException, IOException {
		Messages messages = new Messages();
		
		User user = new User();
		user.setId(30L);
		messages.setSender(user);
		
		User receiver = new User();
		receiver.setId(1L);
		messages.setRecipient(receiver);
		
		messages.setDate(new Date());
		messages.setMessage("Dzień dobry!\nW czym mogę pomóc?");
		messages.setStatus(1L);
		
		Chat chat = new Chat();
		chat.setId(31L);
		messages.setChat(chat);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/messages/addMessage");
		StringEntity stringEntity = new StringEntity(gson.toJson(messages), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpClient.execute(httpPost);
	}
	
	public static void testChat() throws ClientProtocolException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		Scanner scanner = new Scanner(System.in);
		User sender = new User();
		sender.setId(30L);
		User receiver = new User();
		receiver.setId(1L);
		
		
		Chat chat = new Chat();
		chat.setId(31L);;
		
		
		while (true) {
			String word = scanner.nextLine();
			
			if (word == "k") {
				break;
			}
			
			Messages messages = new Messages();
			messages.setDate(new Date());
			messages.setMessage(word);
			messages.setChat(chat);
			messages.setSender(sender);
			messages.setRecipient(receiver);
			messages.setStatus(0L);
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/messages/addMessage");
			httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
			StringEntity stringEntity = new StringEntity(gson.toJson(messages), ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			httpClient.execute(httpPost);
		}
	}
	
	public static void testGlobalChat() throws ClientProtocolException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		Scanner scanner = new Scanner(System.in);
		User sender = new User();
		sender.setId(30L);
		User receiver = User.GLOBAL_CHAT_USER;
		
		
		Chat chat = new Chat();
		chat.setId(70L);;
		
		
		while (true) {
			String word = scanner.nextLine();
			
			if (word == "k") {
				break;
			}
			
			Messages messages = new Messages();
			messages.setDate(new Date());
			messages.setMessage(word);
			messages.setChat(chat);
			messages.setSender(sender);
			messages.setRecipient(receiver);
			messages.setStatus(0L);
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/messages/addMessage");
			httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
			StringEntity stringEntity = new StringEntity(gson.toJson(messages), ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			httpClient.execute(httpPost);
		}
	}
	
	public static void testGlobalChatSecondUser() throws ClientProtocolException, IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
		Scanner scanner = new Scanner(System.in);
		User sender = new User();
		sender.setId(1L);
		User receiver = User.GLOBAL_CHAT_USER;
		
		
		Chat chat = new Chat();
		chat.setId(70L);;
		
		
		while (true) {
			String word = scanner.nextLine();
			
			if (word == "k") {
				break;
			}
			
			Messages messages = new Messages();
			messages.setDate(new Date());
			messages.setMessage(word);
			messages.setChat(chat);
			messages.setSender(sender);
			messages.setRecipient(receiver);
			messages.setStatus(0L);
			
			HttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://localhost:8080/messages/addMessage");
			httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
			StringEntity stringEntity = new StringEntity(gson.toJson(messages), ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			httpClient.execute(httpPost);
		}
	}
	
	public static void addQuestion() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/question/addQuestion");
		Question question = new Question();
		User user = new User();
		user.setId(30L);
		question.setDesignatedUser(user);
		question.setSubject("Nowe pytanie");
		
		StringEntity stringEntity = new StringEntity(new Gson().toJson(question), ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("X-ALLERGY-APP", "RNQdP1DabMnp8n0w");
		httpClient.execute(httpPost);
	}
}
