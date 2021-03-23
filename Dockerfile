FROM openjdk:16-oraclelinux7

COPY *.jar /run

CMD ["java", "-jar", "/run/progress-tracker-app.jar"] 