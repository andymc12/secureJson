package com.example.jaxrs21.secure.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/doc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
public class Resource {

    @GET
    public Document getDoc() {
        return new Document("anybody can see this", "for your eyes only");
    }
}
