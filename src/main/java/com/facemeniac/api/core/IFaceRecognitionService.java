package com.facemeniac.api.core;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface IFaceRecognitionService {
	/**
	 * Esse método compara uma imagem passada como parametro através da URL
	 * com todas as imagens dos criminosos no banco de dados.
	 * Retorna todos os criminosos que são semelhantes a foto.
	 * @param imageUrl
	 * @throws UnirestException 
	 */
	public String Compare(String imageUrl) throws UnirestException;
	public boolean AddNew(String imageUrl, String subjectName) throws UnirestException;
}
