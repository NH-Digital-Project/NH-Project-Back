# ── 1단계: 빌드 ──────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Gradle 의존성 캐시 레이어 (소스 변경 시 재다운로드 방지)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon --quiet

# 소스 빌드 (테스트 제외)
COPY src src
RUN ./gradlew build -x test --no-daemon --quiet

# ── 2단계: 실행 ──────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 보안: root 대신 전용 유저로 실행
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

COPY --from=build /app/build/libs/*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser

ENV TZ=Asia/Seoul
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# 런타임에 반드시 주입해야 하는 환경변수 (값은 docker run -e 또는 docker-compose로 전달)
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV CLIENT_ID=""
ENV CLIENT_SECRET=""
ENV JWT_SECRET=""

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Duser.timezone=Asia/Seoul -jar app.jar"]
