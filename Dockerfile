FROM amazoncorretto:21

WORKDIR /app
COPY target/producto-1.0-SNAPSHOT.jar app.jar
COPY Wallet_fullstack3 /app/oracle_wallet
EXPOSE 8096

CMD [ "java", "-jar", "app.jar" ]