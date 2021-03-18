FROM openjdk:16-oraclelinux7

COPY ./target/*.jar /app

CMD ["java", "-jar", "/app/*.jar"] 