# ZKP Server SDK

ZKP Server SDK Repository에 오신 것을 환영합니다. <br> 이 Repository는 ZKP 기능을 개발하기 위한 Server SDK를 제공합니다.

## 폴더 구조
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
    │   │   ├── did-crypto-sdk-server-2.0.0.jar
    │   ├── gradlew        
    │   ├── gradlew.bat
    │   ├── settings.gradle
    │   └── src
    └── release
        └── did-zkp-sdk-server-2.0.0.jar
```

|  이름 |         역할                    |
| ------- | ------------------------------------ |
| source  |  SDK 소스코드 프로젝트             |
| docs  |   문서            |
| ┖ api  |  API 가이드 문서          |
| README.md  |  프로젝트의 전체적인 개요 설명            |
| CLA.md             | Contributor License Agreement                |
| CHANGELOG.md| 프로젝트 버전별 변경사항           |
| CODE_OF_CONDUCT.md| 기여자의 행동강령            |
| CONTRIBUTING.md| 기여 절차 및 방법           |
| LICENSE                 | Apache 2.0                                      |
| dependencies-license.md| 프로젝트 의존성 라이브러리에 대한 라이선스            |
| MAINTAINERS.md          | 유지관리 가이드              |
| RELEASE-PROCESS.md      | 릴리즈 절차                                |
| SECURITY.md| 보안취약점 보고 및 보안정책            | 

## 빌드 방법
: 본 SDK 그래들 프로젝트이므로 그래들이 설치 되어 있어야 한다.
1. 프로젝트의 `build.gradle` 파일을 열고, 아래와 같이 내용 추가한다.
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
    archiveVersion.set('2.0.0')
    archiveClassifier.set('') 
}

java {
    sourceCompatibility = '21'
    targetCompatibility = '21'
}

dependencies {
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
    implementation 'com.google.code.gson:gson:2.8.9'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation files('libs/did-crypto-sdk-server-2.0.0.jar')
    
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
}
```
* 해당 SDK를 빌드하기 위해선 Crypto-SDK-Server가 필요
2. 사용하는 IDE에서 `Gradle task` 창을 열고, 프로젝트의 `build > build > 태스크를 실행한다.
3. 실행이 완료되면 `%Zkp sdk repository%/build/libs/` 폴더에 `did-zkp-sdk-server-2.0.0.jar` 파일이 생성된다.

<br>

## 라이브러리

라이브러리는 [Releases](https://github.com/OmniOneID/did-zkp-sdk-server/releases) 에서 찾을 수 있습니다.

## ZKP Server SDK
1. 서버 프로젝트의 libs에 did-zkp-sdk-server-2.0.0.jar 파일을 복사한다.
2. 서버 프로젝트의 build gradle에 아래 의존성을 추가한다.

```groovy
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
    implementation 'com.google.code.gson:gson:2.8.9'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation files('libs/did-zkp-sdk-server-2.0.0.jar')
    implementation files('libs/did-crypto-sdk-server-2.0.0.jar')
	
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
```
* 해당 SDK를 사용하기 위해선 Crypto-SDK-Server가 필요
3. `Gradle`을 동기화하여 의존성이 제대로 추가되었는지 확인한다.

## API 참조

API 참조는 [여기](docs/ZKP_SDK_SERVER_API_ko.md)에서 확인할 수 있습니다.

## Change Log

Change Log에는 버전별 변경 사항과 업데이트가 자세히 기록되어 있습니다. 다음에서 확인할 수 있습니다:
- [Change Log](./CHANGELOG.md)  

## 데모 영상 <br>
OpenDID 시스템의 실제 동작을 보여주는 데모 영상은 [Demo Repository](https://github.com/OmniOneID/did-demo-server)에서 확인하실 수 있습니다. <br>
사용자 등록, VC 발급, VP 제출 등 주요 기능들을 영상으로 확인하실 수 있습니다.

## 기여

Contributing 및 pull request 제출 절차에 대한 자세한 내용은 [CONTRIBUTING.md](CONTRIBUTING.md)와 [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) 를 참조하세요.

## 라이선스
[Apache 2.0](LICENSE)

