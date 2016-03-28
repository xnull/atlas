I considered web frameworks:
 1. Spring boot + jersey + jetty
 2. Dropwizard
 3. Play framework
I have decided to use spring boot and the reason is I have spring out of the box and it's a quite lightweight solution.
Dropwizard looks good but you have to do some actions to integrate it with spring.
Play framework - very nice with scala and it's more powerful and it's more web framework than a microservice application I don't need it in my case.

Neo4j is used as a geo spatial database. It provides geo search and it's embedded lightweight, powerful database.

To document api I use swagger - it's easy to use, very powerful. Endpoints:
 - http://localhost:8080/swagger.json - json documentation of the application api.
 - http://localhost:8080/system/ui/index.html - swagger UI, management web UI.

You can run an application by command: ./gradlew bootRun