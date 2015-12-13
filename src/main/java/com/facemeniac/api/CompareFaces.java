package com.facemeniac.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("compare")
public class CompareFaces {

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ResponseBuilder compareImages(@FormParam("image_url") String meg) {
		//IFaceRecognitionService service = new FaceRecognitionService();
		
		System.out.println(meg);
	    return Response.ok();
	}
}
