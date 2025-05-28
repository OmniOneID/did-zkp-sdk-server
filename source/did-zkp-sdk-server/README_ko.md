# ZKP Server SDK Guide
본 문서는 OpenDID ZKP Server SDK 사용을 위한 가이드로, 
Open DID에 필요한 ZKP 관련 기능을 제공한다.


## S/W 사양
| 구분 | 내용                |
|------|----------------------------|
| Language  | Java 21|
| Build System  | Gradle 8.8 |

<br>

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
* 해당 SDK를 빌드하기 위해선 Crypto-Sdk-Server가 필요
2. 사용하는 IDE에서 `Gradle task` 창을 열고, 프로젝트의 `build > build > 태스크를 실행한다.
3. 실행이 완료되면 `%Core repository%/build/libs/` 폴더에 `did-zkp-sdk-server-2.0.0.jar` 파일을 생성된다.

<br>

## SDK 적용 방법
1. 앱 프로젝트의 libs에 did-zkp-sdk-server-2.0.0.jar, did-crypto-sdk-server-2.0.0.jar 파일을 복사한다.
2. 앱 프로젝트의 build gradle에 아래 의존성을 추가한다.

```groovy
    implementation 'org.bouncycastle:bcprov-jdk18on:1.78.1'
    implementation 'org.hibernate:hibernate-validator:7.0.0.Final'
    implementation 'com.google.code.gson:gson:2.8.9'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation files('libs/did-zkp-sdk-server-2.0.0.jar')
    implementation files('libs/did-crypto-sdk-server-2.0.0.jar')
	
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
```
* 해당 SDK를 사용하기 위해선 Crypto-Sdk-Server가 필요
3. `Gradle`을 동기화하여 의존성이 제대로 추가되었는지 확인한다.

<br>

## API 참조

API 참조는 [여기](../../docs/ZKP_SDK_SERVER_API_ko.md)에서 확인할 수 있습니다.
