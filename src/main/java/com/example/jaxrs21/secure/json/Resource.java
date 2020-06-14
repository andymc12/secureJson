package com.example.jaxrs21.secure.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 *
 */
@Path("/doc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
public class Resource {

    static SecurityContext SEC_CTX;

    @Context
    SecurityContext secCtx;

    @GET
    public Document getDoc() {
        SEC_CTX = secCtx;
        return new Document("anybody can see this", "for your eyes only");
    }
}
