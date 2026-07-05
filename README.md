# 3D Project - Route Finder Backend

## 3D 내부 건물 지도 길 안내 프로그램의 백엔드 부분
프론트엔드 부분은 직접 구현하지 않았고 아직 허락을 받지 않았기 때문에 백엔드 구현 부분만 올린 것을 참고해 주시기 바랍니다.

이 서비스는 3D 환경 또는 다층 건물 내에서 최단 경로를 탐색하기 위한 백엔드 기능들을 제공합니다. 다익스트라(Dijkstra) 알고리즘을 활용하여 층간 이동 시 엘리베이터 및 계단 이용 옵션을 고려한 경로를 탐색해 줍니다.

Navmesh 기법은 가장 많이 쓰는 3d 모델링 길찾기 방법이지만 이번 프로젝트는 웹에서도 경로 탐색 공간을 수정할 수 있게 하는것을 목표로 하고 있습니다. Navmesh를 웹에서도 수정할 수 있지만 연산이 많이 들어가기 때문에 비효율적이라고 생각했습니다. 따라서 Navmesh를 이용하지 않고 3d 건물 공간을 노드와 간선만을 이용해서 최단경로 탐색과 수정하는데 필요한 연산을 줄이는것을 고려해서 다음과 같이 만들었습니다.

---

## 🛠️ 기술 스택 및 개발 환경
- **Language**: Java 21
- **Framework**: Spring Boot 3.5.3
- **Build Tool**: Gradle (Wrapper 제공)
- **Database**: MariaDB
- **ORM**: Spring Data JPA (Hibernate)

---

## 📦 핵심 의존성 (Dependencies)
이 프로젝트는 `app/build.gradle`에 다음과 같은 의존성들이 설정되어 있습니다:

| 의존성 | 설명 |
| :--- | :--- |
| **`org.jgrapht:jgrapht-core:1.5.2`** | 가중치 그래프 데이터 구조 제공 및 다익스트라 최단 경로 탐색 핵심 알고리즘 라이브러리 |
| **`org.springframework.boot:spring-boot-starter-web`** | REST API 개발 및 서버(Tomcat) 실행을 위한 Spring MVC 모듈 |
| **`org.springframework.boot:spring-boot-starter-data-jpa`** | 데이터베이스와의 객체 맵핑 및 JPA Repository 활용을 위한 ORM 라이브러리 |
| **`org.mariadb.jdbc:mariadb-java-client:3.5.7`** | MariaDB 데이터베이스 연결용 JDBC 드라이버 |
| **`org.projectlombok:lombok:1.18.34`** | 생성자, Getter/Setter, Logger(`@Slf4j`) 생성 등 보일러플레이트 코드 제거 |
| **`com.google.guava:guava:33.4.6-jre`** | Google의 핵심 유틸리티 라이브러리 |

---

## 🚀 주요 API 명세

### 1. 전체 노드 조회
- **Endpoint**: `GET /api/getNodes`
- **Description**: 시스템에 등록된 모든 노드(위치 좌표 및 정보) 목록을 반환합니다.

### 2. 최단 경로 탐색
- **Endpoint**: `POST /api/findPath`
- **Request Body**:
  ```json
  {
    "startPoint": "시작 노드 이름",
    "destination": "목적지 노드 이름",
    "useElevator": true
  }
  ```
- **Description**: 엘리베이터 탑승 여부에 따라 최적의 경로를 다익스트라 알고리즘을 사용해 탐색한 후, 거치는 노드 목록을 순서대로 반환합니다.

### 3. 노드 검색 (정확한 매칭)
- **Endpoint**: `POST /api/nodes/find`
- **Request Body**:
  ```json
  {
    "nodeName": "검색할 노드 이름"
  }
  ```
- **Description**: 이름이 정확히 일치하는 단일 노드를 반환합니다.

### 4. 키워드 기반 노드 검색 (부분 매칭)
- **Endpoint**: `GET /api/search?keyword={검색어}`
- **Description**: 입력된 키워드가 이름에 포함된 모든 노드 목록을 검색합니다.

---

## 🏗️ 실행 방법

### 로컬 실행
프로젝트 루트 폴더에서 아래 명령어를 실행하여 애플리케이션을 구동합니다:

```bash
# Windows (PowerShell)
.\gradlew.bat bootRun

# macOS / Linux
./gradlew bootRun
```
