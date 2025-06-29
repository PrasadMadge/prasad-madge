# Base image with JDK 24
FROM eclipse-temurin:24-jdk

# Install Maven manually
ARG MAVEN_VERSION=3.9.10
ENV MAVEN_HOME=/opt/apache-maven-${MAVEN_VERSION}
ENV PATH=${MAVEN_HOME}/bin:${PATH}

# Install curl, unzip, Maven, and Allure CLI
RUN apt-get update && apt-get install -y curl unzip wget \
    && curl -fsSL https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip -o maven.zip \
    && unzip maven.zip -d /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn \
    && rm maven.zip \
    && wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz \
    && tar -xzf allure-2.27.0.tgz -C /opt \
    && mv /opt/allure-2.27.0 /opt/allure \
    && ln -s /opt/allure/bin/allure /usr/bin/allure \
    && rm allure-2.27.0.tgz \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /usr/src/app

# Copy all files
COPY . .

# Run your bash script
CMD ["bash", "run-tests.sh"]