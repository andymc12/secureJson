package com.example.jaxrs21.secure.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class SecureJsonContextResolver implements ContextResolver<Jsonb> {

    /*
    App app;

    public SecureJsonContextResolver(@Context Application app) {
        this.app = (App) app;
    }
    */

    @Context
    SecurityContext secCtx;  // this should work, or at least ctor-injected secCtx should, but isn't...

    @Override
    public Jsonb getContext(Class<?> type) {
        try {
        //SecurityContext secCtx = ((App) app).secCtx;
        System.out.println("ContextResolver.getContext() - user = " + secCtx.getUserPrincipal().getName());
        JsonbConfig config = new JsonbConfig().withPropertyVisibilityStrategy(
            new SecurePropertyVisibilityStrategy(secCtx));
        return JsonbBuilder.newBuilder()
                           .withConfig(config)
                           .build();
        } catch (Throwable t) {
            throw new WebApplicationException(t, Response.status(500).entity(throwableToString(t)).build());
        }
    }

    private String throwableToString(Throwable t) {
        StringBuilder sb = new StringBuilder(t.toString()).append(System.lineSeparator());
        for (StackTraceElement ste : t.getStackTrace()) {
            sb.append("  at ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(")
              .append(ste.getFileName()).append(":").append(ste.getLineNumber()).append(")")
              .append(System.lineSeparator());
        }
        return sb.toString();
    }
}