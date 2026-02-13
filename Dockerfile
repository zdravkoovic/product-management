FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/product-1.0.jar /app

EXPOSE 8050

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "product-1.0.jar"]