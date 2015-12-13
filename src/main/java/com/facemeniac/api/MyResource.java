package com.facemeniac.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.facemeniac.api.core.FaceRecognitionService;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/compare")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/{param}")
    public Response getIt(@PathParam("param") String msg) {
    	//DoCompare();
    	
    	return Response.status(200).entity(DoCompare("http://tudopedreirorj.netai.net/" + msg + ".jpg")).build();
    }
    
    public String DoCompare(String url) {
    	try {
    		FaceRecognitionService f = new FaceRecognitionService();
    		//f.AddNew("http://i.telegraph.co.uk/multimedia/archive/01242/franck_ribery_1242993a.jpg", "ribery");
			return f.Compare(url);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
