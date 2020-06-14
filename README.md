# secureJson
A demo showing how to secure certain JSON fields using JAX-RS 2.1 and JSON-B 1.0 in Open Liberty

This works because the `@RolesAllowed("SecretRole")` annotation is placed on a getter method for a POJO that is returned from a JAX-RS resource method.
The `ContextResolver<Jsonb>` creates the `Jsonb` object that is configured with a `PropertyVisibilityStrategey` that filters what it serializes based on whether the current user is in a role in the `@RolesAllowed` annotation - or always serializes a field if no annotation is present.
