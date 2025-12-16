FROM amazoncorretto:17-alpine3.15
LABEL MANTAINER="Vektrom Bonilha"

WORKDIR /app

EXPOSE 8081

RUN apk add --no-cache curl

RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'

COPY spring/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]