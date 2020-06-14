package com.example.jaxrs21.secure.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class SecureJsonContextResolver implements ContextResolver<Jsonb> {

    App app;

    public SecureJsonContextResolver(@Context Application app) {
        this.app = (App) app;
    }

    /*
    @Context
    SecurityContext secCtx;  // this should work, or at least ctor-injected secCtx should, but isn't...

    public SecureJsonContextResolver(@Context SecurityContext secCtx) {
        this.secCtx = secCtx;
    }
    */

    @Override
    public Jsonb getContext(Class<?> type) {
        SecurityContext secCtx = ((App) app).secCtx;
        System.out.println("ContextResolver.getContext() - user = " + secCtx.getUserPrincipal().getName());
        JsonbConfig config = new JsonbConfig().withPropertyVisibilityStrategy(
            new SecurePropertyVisibilityStrategy(secCtx));
        return JsonbBuilder.newBuilder()
                           .withConfig(config)
                           .build();
    }
    
}