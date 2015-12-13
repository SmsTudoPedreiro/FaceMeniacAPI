package com.facemeniac.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.facemeniac.api.core.FaceRecognitionService;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/form/compare")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	DoCompare();
    	
    	
    	
    	return "Hello, Heroku!";
    }
    
    public void DoCompare() {
    	try {
    		FaceRecognitionService f = new FaceRecognitionService();
    		//f.AddNew("http://i.telegraph.co.uk/multimedia/archive/01242/franck_ribery_1242993a.jpg", "ribery");
			f.Compare("http://www.impactonline.co/images/articles/people/gollumx2.jpg");
		} catch (UnirestException e) {
			e.printStackTrace();
		}
    }
}
