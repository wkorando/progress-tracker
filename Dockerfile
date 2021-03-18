FROM openjdk:16-oraclelinux7

COPY *.jar /app

CMD ["java", "-jar", "/app/*.jar"] 