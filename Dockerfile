FROM openjdk:16-oraclelinux7

COPY progress-tracker-app.jar /app

CMD ["java", "-jar", "/app/progress-tracker-app*.jar"] 