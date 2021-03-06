#FROM openjdk:11
#COPY ./presentation/target/presentation-0.0.1-SNAPSHOT.jar presentation-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","presentation-0.0.1-SNAPSHOT.jar"]

FROM adoptopenjdk/openjdk11:alpine-jre as builder
# First stage : Extract the layers
WORKDIR application
ARG JAR_FILE=./presentation/target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk/openjdk11:alpine-jre as runtime
# General information
#LABEL maintainer="contact@redamessoudi.com"
#LABEL com.redamessoudi.application.name = "optimizedSBDimage"
#LABEL com.redamessoudi.application.version = "0.0.3"
# User management
ARG USER=jgoncalves
ARG HOME=/home/$USER
## Create a group and user
RUN addgroup --gid 1000 "$USER" \
  && adduser -u 1000 -G "$USER" -h "$HOME" "$USER" --disabled-password
RUN chown $USER:$USER $HOME
USER $USER
WORKDIR $HOME
# Second stage : Copy the extracted layers
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader ./
COPY --from=builder application/organization-dependencies ./
COPY --from=builder application/modules-dependencies ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/presentation/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
