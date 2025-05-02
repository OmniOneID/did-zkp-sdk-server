# ZKP Server SDK Guide

This document is a guide for using the OpenDID ZKP Server SDK, which provides ZKP-related features required for OpenDID.

## S/W Specifications

| Category     | Details    |
| ------------ | ---------- |
| Language     | Java 17    |
| Build System | Gradle 8.8 |

<br>

## Build Instructions

Since this SDK is a Gradle project, Gradle must be installed.

1. Open the `build.gradle` file of the project and add the following content:

```groovy
plugins {
    id 'java-library'
}

repositories {
    jcenter()
}

group = 'org.omnione.did'

jar {
    archiveBaseName.set('did-zkp-sdk-server')
    archiveVersion.set('1.0.0')
    archiveClassifier.set('')
}

java {
    sourceCompatibility = '17'
    targetCompatibility = '17'
}

dependencies {
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
    implementation 'com.google.code.gson:gson:2.8.9'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation files('libs/did-crypto-sdk-server-1.0.0.jar')

    annotationProcessor 'org.projectlombok:lombok:1.18.24'
}
```

* **Note:** Crypto-Sdk-Server is required to build this SDK.

2. Open the `Gradle task` window in your IDE, and execute the `build > build` task of the project.

3. Once the execution is complete, the `did-zkp-sdk-server-1.0.0.jar` file will be created in the `%Core repository%/build/libs/` folder.

<br>

## SDK Integration Instructions

1. Copy the `did-zkp-sdk-server-1.0.0.jar` and `did-crypto-sdk-server-1.0.0.jar` files into the `libs` folder of your application project.

2. Add the following dependencies to your application's build.gradle file:

```groovy
implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
implementation 'com.google.code.gson:gson:2.8.9'
implementation 'org.projectlombok:lombok:1.18.24'
implementation files('libs/did-zkp-sdk-server-1.0.0.jar')
implementation files('libs/did-crypto-sdk-server-1.0.0.jar')

annotationProcessor 'org.projectlombok:lombok:1.18.24'
```

* **Note:** Crypto-Sdk-Server is required to use this SDK.

3. Synchronize your Gradle project and ensure that the dependencies are correctly added.

<br>

## API Reference

You can find the API reference [here](../../docs/ZKP_SDK_SERVER_API.md).
