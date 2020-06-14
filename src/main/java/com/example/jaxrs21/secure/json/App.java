package com.example.jaxrs21.secure.json;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@ApplicationPath("/app")
public class App extends Application {
    @Context
    SecurityContext secCtx;
}
