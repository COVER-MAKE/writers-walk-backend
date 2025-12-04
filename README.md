# AI를 활용한 도서 관리 시스템 (Backend)

## 프로젝트 개요
이 프로젝트는 **Spring Boot**와 **React**를 활용한 풀스택 웹 애플리케이션의 백엔드 서버입니다.  
사용자가 도서를 등록, 조회, 수정, 삭제(CRUD)할 수 있는 기본 기능을 제공하며, **OpenAI의 DALL-E 모델**을 통해 생성된 도서 표지 이미지의 URL을 저장하고 관리하는 기능을 수행합니다.

### 핵심 목표
* **RESTful API 설계:** 프론트엔드와의 원활한 데이터 통신을 위한 API 구현.
* **데이터 관리:** JPA와 H2 Database를 활용한 도서 데이터의 저장 및 관리.
* **AI 서비스 연동 지원:** 프론트엔드에서 생성한 AI 이미지 URL을 DB에 업데이트 및 저장.
* **안정적인 서버 구축:** 예외 처리(Global Exception Handling) 및 트랜잭션 관리.

---

## 기술 스택 (Tech Stack)

| 구분 | 기술 | 설명 |
| :-- | :-- | :-- |
| **Language** | Java 17 | Backend 주 언어 |
| **Framework** | Spring Boot 3.x | 웹 애플리케이션 프레임워크 |
| **Database** | H2 Database | 개발용 인메모리 데이터베이스 |
| **ORM** | Spring Data JPA | 객체 중심의 데이터 조작 |
| **API** | REST API | 자원 중심의 API 설계 |
| **Tool** | Gradle | 빌드 관리 도구 |

---

## 📂 프로젝트 구조 (Project Structure)

```text
src/main/java/com/example/bookmanager
├── api
│   ├── controller      # API 요청 처리 (BookController)
│   └── dto             # 데이터 전송 객체
├── config              # 설정 파일 (WebConfig - CORS 등)
├── domain              # 엔티티 클래스 (Book)
├── exception           # 전역 예외 처리 (GlobalExceptionHandler)
├── repository          # DB 접근 계층 (BookRepository)
└── service             # 비즈니스 로직 (BookService)