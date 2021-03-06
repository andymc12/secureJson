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

This version shows a NullPointerException when doing this the "right" way - by injecting the `SecurityContext` into the
`ContextResolver<Jsonb>` provider.  Commented out is the working-yet-more-convoluted way of injecting the
`SecurityContext` into the `Application` subclass, and the injecting the `Application` into the context resolver
provider.

When I run this I see the following NullPointerException:
```
java.lang.NullPointerException
  at org.apache.cxf.jaxrs.impl.tl.ThreadLocalSecurityContext.getUserPrincipal(ThreadLocalSecurityContext.java:34)
  at com.example.jaxrs21.secure.json.SecureJsonContextResolver.getContext(SecureJsonContextResolver.java:31)
  at com.example.jaxrs21.secure.json.SecureJsonContextResolver.getContext(SecureJsonContextResolver.java:1)
  at com.ibm.ws.jaxrs21.providers.json.JsonBProvider.getJsonb(JsonBProvider.java:212)
  at com.ibm.ws.jaxrs21.providers.json.JsonBProvider.writeTo(JsonBProvider.java:201)
  at org.apache.cxf.jaxrs.utils.JAXRSUtils$3.run(JAXRSUtils.java:1497)
  at org.apache.cxf.jaxrs.utils.JAXRSUtils$3.run(JAXRSUtils.java:1494)
  at java.security.AccessController.doPrivileged(AccessController.java:703)
  at org.apache.cxf.jaxrs.utils.JAXRSUtils.writeMessageBody(JAXRSUtils.java:1494)
  at org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor.serializeMessage(JAXRSOutInterceptor.java:257)
  at org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor.processResponse(JAXRSOutInterceptor.java:128)
  at org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor.handleMessage(JAXRSOutInterceptor.java:85)
  at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:316)
  at org.apache.cxf.interceptor.OutgoingChainInterceptor.handleMessage(OutgoingChainInterceptor.java:93)
  at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:316)
  at org.apache.cxf.transport.ChainInitiationObserver.onMessage(ChainInitiationObserver.java:123)
  at org.apache.cxf.transport.http.AbstractHTTPDestination.invoke(AbstractHTTPDestination.java:273)
  at com.ibm.ws.jaxrs20.endpoint.AbstractJaxRsWebEndpoint.invoke(AbstractJaxRsWebEndpoint.java:136)
  at com.ibm.websphere.jaxrs.server.IBMRestServlet.handleRequest(IBMRestServlet.java:146)
  at com.ibm.websphere.jaxrs.server.IBMRestServlet.doGet(IBMRestServlet.java:112)
  at javax.servlet.http.HttpServlet.service(HttpServlet.java:686)
  at com.ibm.websphere.jaxrs.server.IBMRestServlet.service(IBMRestServlet.java:96)
  at com.ibm.ws.webcontainer.servlet.ServletWrapper.service(ServletWrapper.java:1230)
  at com.ibm.ws.webcontainer.servlet.ServletWrapper.handleRequest(ServletWrapper.java:729)
  at com.ibm.ws.webcontainer.servlet.ServletWrapper.handleRequest(ServletWrapper.java:426)
  at com.ibm.ws.webcontainer.filter.WebAppFilterChain.invokeTarget(WebAppFilterChain.java:182)
  at com.ibm.ws.webcontainer.filter.WebAppFilterChain.doFilter(WebAppFilterChain.java:93)
  at com.ibm.ws.security.jaspi.JaspiServletFilter.doFilter(JaspiServletFilter.java:56)
  at com.ibm.ws.webcontainer.filter.FilterInstanceWrapper.doFilter(FilterInstanceWrapper.java:201)
  at com.ibm.ws.webcontainer.filter.WebAppFilterChain.doFilter(WebAppFilterChain.java:90)
  at com.ibm.ws.webcontainer.filter.WebAppFilterManager.doFilter(WebAppFilterManager.java:1001)
  at com.ibm.ws.webcontainer.filter.WebAppFilterManager.invokeFilters(WebAppFilterManager.java:1139)
  at com.ibm.ws.webcontainer.webapp.WebApp.handleRequest(WebApp.java:5037)
  at com.ibm.ws.webcontainer.osgi.DynamicVirtualHost$2.handleRequest(DynamicVirtualHost.java:314)
  at com.ibm.ws.webcontainer.WebContainer.handleRequest(WebContainer.java:1007)
  at com.ibm.ws.webcontainer.osgi.DynamicVirtualHost$2.run(DynamicVirtualHost.java:279)
  at com.ibm.ws.http.dispatcher.internal.channel.HttpDispatcherLink$TaskWrapper.run(HttpDispatcherLink.java:1134)
  at com.ibm.ws.http.dispatcher.internal.channel.HttpDispatcherLink.wrapHandlerAndExecute(HttpDispatcherLink.java:415)
  at com.ibm.ws.http.dispatcher.internal.channel.HttpDispatcherLink.ready(HttpDispatcherLink.java:374)
  at com.ibm.ws.http.channel.internal.inbound.HttpInboundLink.handleDiscrimination(HttpInboundLink.java:546)
  at com.ibm.ws.http.channel.internal.inbound.HttpInboundLink.handleNewRequest(HttpInboundLink.java:480)
  at com.ibm.ws.http.channel.internal.inbound.HttpInboundLink.processRequest(HttpInboundLink.java:345)
  at com.ibm.ws.http.channel.internal.inbound.HttpInboundLink.ready(HttpInboundLink.java:316)
  at com.ibm.ws.tcpchannel.internal.NewConnectionInitialReadCallback.sendToDiscriminators(NewConnectionInitialReadCallback.java:167)
  at com.ibm.ws.tcpchannel.internal.NewConnectionInitialReadCallback.complete(NewConnectionInitialReadCallback.java:75)
  at com.ibm.ws.tcpchannel.internal.WorkQueueManager.requestComplete(WorkQueueManager.java:504)
  at com.ibm.ws.tcpchannel.internal.WorkQueueManager.attemptIO(WorkQueueManager.java:574)
  at com.ibm.ws.tcpchannel.internal.WorkQueueManager.workerRun(WorkQueueManager.java:958)
  at com.ibm.ws.tcpchannel.internal.WorkQueueManager$Worker.run(WorkQueueManager.java:1047)
  at com.ibm.ws.threading.internal.ExecutorServiceImpl$RunnableWrapper.run(ExecutorServiceImpl.java:239)
  at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
  at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
  at java.lang.Thread.run(Thread.java:825)
```
