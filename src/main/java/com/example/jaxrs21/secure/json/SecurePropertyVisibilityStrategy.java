package com.example.jaxrs21.secure.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.json.bind.config.PropertyVisibilityStrategy;
import javax.ws.rs.core.SecurityContext;

public class SecurePropertyVisibilityStrategy implements PropertyVisibilityStrategy {

    private SecurityContext secCtx;

    SecurePropertyVisibilityStrategy(SecurityContext secCtx) {
        this.secCtx = secCtx;
    }

    @Override
    public boolean isVisible(Field field) {
        return false;
    }

    @Override
    public boolean isVisible(Method method) {
        RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null) {
            return true;
        }
        return Stream.of(rolesAllowed.value()).anyMatch(secCtx::isUserInRole);
    }
}