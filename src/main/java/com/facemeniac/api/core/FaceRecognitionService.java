package com.facemeniac.api.core;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facemeniac.api.core.model.DefectResultModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

// Scarlett Johansson 1 > http://si.wsj.net/public/resources/images/BN-BY925_mag041_OZ_20140318165119.jpg
// Scarlett Johansson 2 > http://images.virgula.uol.com.br/2014/11/scarlett-johansson-jpg.jpg
// Einstein: http://opiniaoenoticia.com.br/wp-content/uploads/einstein5.jpg

public class FaceRecognitionService implements IFaceRecognitionService {
	// Constantes
	protected static final String API_KEY = "0f8558377b42fdad1804207be01b711e";
	protected static final String API_KEY_HASH = "05rwWuExM7msh8x5i1GvbAIQejmgp1vRG3njsnfopCqhMqmL9E";
	protected static final String GALLERY_NAME = "mainGallery";
	protected static final String GALLERY_NAME_TEST = "testGallery";
	protected static final String SUCCESS_FLAG = "Complete";
	
	// Membros
	protected DefectResultModel results;
	private String subjectName;
	
	// Construtor
	public FaceRecognitionService()
	{
		this.subjectName = "subjectTemp";
	}
	
	// Métodos
	public boolean Compare(String imageUrl) throws UnirestException {
		if (DetectFace(imageUrl) == false) { return false; }
		if (AddNewFaceToGallery() == false) { return false; }
		if (PerformRecognition() == false) { return false; }
		RemoveSubject();
		
		return true;
	}
	public boolean AddNew(String imageUrl, String subjectName) throws UnirestException {
		this.subjectName = subjectName;
		
		if (DetectFace(imageUrl) == false) { return false; }
		if (AddNewFaceToGallery() == false) { return false; }
		
		this.subjectName = "subjectTemp";
		
		return true;
	}
	// Detects a face, insert it on specified gallery and return it's id.
	protected boolean DetectFace(String imageUrl) throws UnirestException {
		// Sending request:
		HttpResponse<JsonNode> response = Unirest.post("https://animetrics.p.mashape.com/detect?api_key=0f8558377b42fdad1804207be01b711e")
			.header("X-Mashape-Key", FaceRecognitionService.API_KEY_HASH)
			.header("Content-Type", "application/x-www-form-urlencoded")
			.header("Accept", "application/json")
			.field("selector", "FULL")
			.field("url", imageUrl)
			.asJson();
		
		try {
			// Confirmating information:
			JSONObject responseObj = response.getBody().getObject();
			JSONArray images = responseObj.getJSONArray("images");
			DefectResultModel model = new DefectResultModel();
			
			String status = images.getJSONObject(0).getString("status");
			if (status.equals(SUCCESS_FLAG) == true) {
				// Salva os dados nos atributos da classe
				model.setImage_id(images.getJSONObject(0).getString("image_id"));
				model.setHeight((int)images.getJSONObject(0).getJSONArray("faces").getJSONObject(0).get("height"));
				model.setWidth((int)images.getJSONObject(0).getJSONArray("faces").getJSONObject(0).get("width"));
				model.setTopLeftX((int)images.getJSONObject(0).getJSONArray("faces").getJSONObject(0).get("topLeftX"));
				model.setTopLeftY((int)images.getJSONObject(0).getJSONArray("faces").getJSONObject(0).get("topLeftY"));
				
				this.results = model;
				return true;
			}
		}
		catch (Exception e) { return false; }
		
		this.results = null;
		return false;
	}
	protected boolean AddNewFaceToGallery() throws UnirestException {
		// Envia requisição de Enroll:
		HttpResponse<JsonNode> response = Unirest.get("https://animetrics.p.mashape.com/enroll?api_key=0f8558377b42fdad1804207be01b711e&gallery_id=" + GALLERY_NAME + "&height=" + results.getHeight() + "&image_id=" + results.getImage_id() + "&subject_id=" + this.subjectName + "&topLeftX=" + results.getTopLeftX() + "&topLeftY=" + results.getTopLeftY() + "&width=" + results.getWidth())
			.header("X-Mashape-Key", FaceRecognitionService.API_KEY_HASH)
			.header("Accept", "application/json")
			.asJson();
		
		// Confirmando dados:
		JSONObject responseObj = response.getBody().getObject();
		JSONArray images = responseObj.getJSONArray("images");

		return true;
	}
	protected boolean PerformRecognition() throws UnirestException {
		HttpResponse<JsonNode> response = Unirest.get("https://animetrics.p.mashape.com/recognize?api_key=0f8558377b42fdad1804207be01b711e&gallery_id=" + GALLERY_NAME + "&height=" + results.getHeight() + "&image_id=" + results.getImage_id() + "&subject_id=" + this.subjectName + "&topLeftX=" + results.getTopLeftX() + "&topLeftY=" + results.getTopLeftY() + "&width=" + results.getWidth())
			.header("X-Mashape-Key", "05rwWuExM7msh8x5i1GvbAIQejmgp1vRG3njsnfopCqhMqmL9E")
			.header("Accept", "application/json")
			.asJson();
		
		// Confirmando dados
		JSONObject responseObj = response.getBody().getObject();		
		JSONArray images = responseObj.getJSONArray("images");
		
		try {
			JSONObject objects = (JSONObject) images.getJSONObject(0).get("candidates");
		
			@SuppressWarnings("unchecked")
			Iterator<String> nameItr = objects.keys();
			Map<String, Double> outMap = new HashMap<String, Double>();
			String miliantName = "";
			double percentage = 0;
			
			while(nameItr.hasNext() == true) {
			   String name = nameItr.next();
			   if (name.equals("subjectTemp") == false) {
				   double percentageValue = objects.getDouble(name);
				   outMap.put(name, percentageValue);
				   
				   if (percentageValue > percentage) {
					   percentage = percentageValue;
					   miliantName = name;
				   }
			   }
			}
			
			System.out.println("Nome do miliante!! > " + miliantName);
			
			return true;
			
		} catch(Exception e) {
			System.out.println("catch");
			return false;
			
		}
		
	}
	protected void RemoveSubject() throws UnirestException {
		HttpResponse<JsonNode> response = Unirest.get("https://animetrics.p.mashape.com/remove_from_gallery?api_key=" + API_KEY + "&gallery_id=" + GALLERY_NAME + "&subject_id=" + this.subjectName)
			.header("X-Mashape-Key", "05rwWuExM7msh8x5i1GvbAIQejmgp1vRG3njsnfopCqhMqmL9E")
			.header("Accept", "application/json")
			.asJson();
	}
	
	
	protected void GetGalleries() {
		
	}
	protected void GetSubjects(String galleryName) {
		
	}
	protected void GetFaces(String subjectName) {
		
	}
	protected void RecognizeFace(String imageUrl) {
		
	}
}
