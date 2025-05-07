# ZKP Server SDK

Welcome to the ZKP Server SDK Repository.  
This repository provides an server SDK for developing ZKP functions.

## Folder Structure
```
did-zkp-sdk-server
├── CHANGELOG.md
├── CLA.md
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
├── LICENSE
├── dependencies-license.md
├── MAINTAINERS.md
├── README.md
├── README_ko.md
├── RELEASE-PROCESS.md
├── SECURITY.md
├── docs
│   └── api
│       ├── ZKP_SDK_SERVER_API.md
│       ├── ZKP_SDK_SERVER_API_ko.md
│       └── ZKPSDKError.md
└── source
    ├── did-zkp-sdk-server
    │   ├── README.md
    │   ├── README_ko.md
    │   ├── build.gradle
    │   ├── gradle
    │   │   └── wrapper
    │   ├── .gitignore
    │   ├── build
    │   ├── libs
    │   │   ├── did-crypto-sdk-server-1.0.0.jar
    │   ├── gradlew        
    │   ├── gradlew.bat
    │   ├── settings.gradle
    │   └── src
    └── release
        └── did-zkp-sdk-server-1.0.0.jar
```

| Name                    | Role                                             |
|-------------------------|--------------------------------------------------|
| source                  | SDK source code project                          |
| docs                    | Documentation                                    |
| ┖ api                   | API guide documents                              |
| README.md               | General overview of the project                  |
| CLA.md                  | Contributor License Agreement                    |
| CHANGELOG.md            | Project version history                          |
| CODE_OF_CONDUCT.md      | Contributor Code of Conduct                      |
| CONTRIBUTING.md         | Contribution procedures and methods              |
| LICENSE                 | Apache 2.0                                       |
| dependencies-license.md | License information for project dependencies     |
| MAINTAINERS.md          | Maintainer guide                                 |
| RELEASE-PROCESS.md      | Release process                                  |
| SECURITY.md             | Security policy and vulnerability reporting      |

## Build Instructions

> This SDK is a Gradle project, so Gradle must be installed.

1. Open the `build.gradle` file and add the following contents:
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
* You need the Crypto-SDK-Server to build this SDK.

2. Open the `Gradle task` window in your IDE and execute the `build > build` task of the project.

3. Once the build completes, the file `did-zkp-sdk-server-1.0.0.jar` will be created in the `%Zkp sdk repository%/build/libs/` directory.

<br>

## Library

You can find the library on the [Releases](https://github.com/OmniOneID/did-zkp-sdk-server/releases) page.

## Using the ZKP Server SDK

1. Copy the `did-zkp-sdk-server-1.0.0.jar` file to the `libs` folder of your server project.
2. Add the following dependencies to your server project's build.gradle:

```groovy
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
    implementation 'com.google.code.gson:gson:2.8.9'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation files('libs/did-zkp-sdk-server-1.0.0.jar')
    implementation files('libs/did-crypto-sdk-server-1.0.0.jar')

    annotationProcessor 'org.projectlombok:lombok:1.18.24'
```

* You need the Crypto-SDK-Server to use this SDK.

3. Sync your Gradle project and ensure the dependencies are properly added.

## API Reference

You can check the API reference [here](docs/ZKP_SDK_SERVER_API.md).

## Change Log

All changes and updates per version are documented in the changelog:  
- [Change Log](./CHANGELOG.md)  

## Demo Video  
You can find a demo of the OpenDID system’s actual operations in the [Demo Repository](https://github.com/OmniOneID/did-demo-server).  
The demo includes core features such as user registration, VC issuance, and VP submission.

## Contributing

For details on how to contribute and submit pull requests, refer to [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## License

[Apache 2.0](LICENSE)
