#!/bin/bash

# Run tests
mvn clean verify

# Timestamp
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
OUTPUT_DIR="allure-history/${TIMESTAMP}"

# Generate report in timestamped folder
allure generate allure-results --clean -o "$OUTPUT_DIR"

echo "Allure report generated at: $OUTPUT_DIR/index.html"