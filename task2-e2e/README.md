# 📱 task2-e2e: Mobile App Automation Framework

This project is an automated end-to-end testing framework for a Mobile application using **Appium**, **Java**, **TestNG**, and **Allure**.

---
## 🎥 Screen recording

[Video](https://drive.google.com/file/d/1Xj_uVJXhqhbZ7k0YFQcsqphMT1eIAOnv/view?usp=sharing)

---
## ⚙️ Project Setup

### ✅ Prerequisites

Make sure the following tools and environments are set up on your machine:

- [JDK (Latest version)](https://adoptopenjdk.net/)
- [Node.js](https://nodejs.org/) 
- [Appium Server](https://appium.io/docs/en/about-appium/intro/)
- [Maven](https://maven.apache.org/download.cgi)
- [Android SDK](https://developer.android.com/studio)
- `adb` (Android Debug Bridge) setup and added to system path
- An Android device or emulator connected and accessible via `adb devices`

---

## ▶️ How to Run the Tests and View Reports 📊

- Edit **config.yaml** with correct app info and appium, node path
- Connect the android device and make it accessible via adb.
- **Simply execute the following command from the project root**

```bash
mvn clean verify
```
- Run to view report in allure-report folder
```bash
allure generate allure-results --clean -o allure-report
```
---

## 🧠 Approach

This section describes the overall approach taken for automating the Monefy Android app using Appium and Java. 

- **Manual exploration** of the app to understand UI behavior and navigation.
- **App launch via Appium** using desired capabilities and the `.apk` file.
- **Page Object Model (POM)** structure to encapsulate page interactions and locators.
- **Locator extraction** using Appium Inspector and implementation of action methods in corresponding page classes.
- **Test creation** with TestNG for validating user flows.
- **Logging setup** with Log4J for traceable execution and debugging.
- **Allure integration** for rich and interactive reporting post-execution.
---

## 🧩 Why This Stack?
- **Appium**: Supports iOS/Android both, nice client libraries, mature ecosystem, good community support
- **Java**: Mature ecosystem, good community support, easy to onboard/hire new talent.
- **TestNG**: Powerful test runner with annotation support.
---

## ⚠️  Key Challenges and Resolutions

1. **Custom numeric input** handling was complex due to the app’s built-in keyboard, not a native system keyboard.
2. **Ad banners and “Getting Started” screens** disrupted test flow on a fresh app install, so special dismiss logic was added.
3. **StaleElementReferenceException** was frequent on screen transitions, handled via retries and dynamic waits.
4. **App reset before each test** was not achievable using `appPackage` and `appActivity` for an un-signed app. The solution was to use the full `.apk` path via the `app` capability with `fullReset=true`.
5. **Support for iOS app** is still not added, needs refactoring up the correct Driver and capabilities
---

## Project Structure

The project is organized as follows:

```plaintext
task2-e2e/
├── build/
│   └── monefy.apk / build
├── config/
│   └── config.yaml / main config file
├── reports/
│   └── Allure reports / reports
├── src/
│   └── test/
│       └── java/
│           ├── base / base setup for running test
│           ├── pages / UI locators and action helper for each page
│           ├── tests/
│           │   ├── BudgetTests.java
│           │   └── ExpenseTests.java
│           └── utils / Helper methods
├── pom.xml
└── testng.xml
```

## 👨‍💻 Maintainer
Built with ❤️ by Prasad M
---