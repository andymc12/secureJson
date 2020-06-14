# secureJson
A demo showing how to secure certain JSON fields using JAX-RS 2.1 and JSON-B 1.0 in Open Liberty

This works because the `@RolesAllowed("SecretRole")` annotation is placed on a getter method for a POJO that is returned from a JAX-RS resource method.
The `ContextResolver<Jsonb>` creates the `Jsonb` object that is configured with a `PropertyVisibilityStrategey` that filters what it serializes based on whether the current user is in a role in the `@RolesAllowed` annotation - or always serializes a field if no annotation is present.

To test this, clone or download this repo, then do: `mvn clean package liberty:run`
Open your browser to: http://localhost:9080/
Click the "JAX-RS endpoint" link - that will direct you to a login page - use user1/user1pwd for the username/password.
That will show you a filtered view of the document.
Go back to the index page and click the log out button, and then click the "JAX-RS endpoint" link again, this time, use user2/user2pwd.
Now you should see the "secret data" too.
