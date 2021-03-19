FROM openjdk:16-oraclelinux7

COPY progress-tracker-app.jar /run

CMD ["java", "-jar", "/run/progress-tracker-app.jar"] 