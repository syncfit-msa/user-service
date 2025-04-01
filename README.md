# 📂 USER-SERVICE

## 서비스 개요
`USER-SERVICE`는 사용자와 관련된 모든 데이터를 처리하는 서비스입니다. 사용자의 정보 관리, 인증 및 권한 관리, 회원가입, 로그인 등을 담당합니다. 또한, 관리자 및 멤버 관련 API를 제공합니다.

## 디렉터리 구조
```
📦domain
 ┣ 📂admin
 ┃ ┣ 📂api
 ┃ ┃ ┗ 📜AdminController.java
 ┃ ┣ 📂application
 ┃ ┃ ┗ 📜AdminService.java
 ┃ ┗ 📂dto
 ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┗ 📜MemberInfoResponse.java
 ┣ 📂auth
 ┃ ┣ 📂api
 ┃ ┃ ┗ 📜AuthController.java
 ┃ ┣ 📂application
 ┃ ┃ ┣ 📜AuthService.java
 ┃ ┃ ┣ 📜IdTokenVerifier.java
 ┃ ┃ ┣ 📜JwtTokenService.java
 ┃ ┃ ┗ 📜KakaoService.java
 ┃ ┣ 📂dao
 ┃ ┃ ┗ 📜RefreshTokenRepository.java
 ┃ ┣ 📂domain
 ┃ ┃ ┗ 📜RefreshToken.java
 ┃ ┗ 📂dto
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┗ 📜AuthCodeRequest.java
 ┃ ┃ ┣ 📂response
 ┃ ┃ ┃ ┣ 📜IdTokenResponse.java
 ┃ ┃ ┃ ┗ 📜SocialLoginResponse.java
 ┃ ┃ ┣ 📜AccessTokenDto.java
 ┃ ┃ ┗ 📜RefreshTokenDto.java
 ┣ 📂common
 ┃ ┗ 📂model
 ┃ ┃ ┗ 📜BaseTimeEntity.java
 ┗ 📂member
 ┃ ┣ 📂api
 ┃ ┃ ┗ 📜MemberController.java
 ┃ ┣ 📂application
 ┃ ┃ ┗ 📜MemberService.java
 ┃ ┣ 📂dao
 ┃ ┃ ┗ 📜MemberRepository.java
 ┃ ┣ 📂domain
 ┃ ┃ ┣ 📜Member.java
 ┃ ┃ ┣ 📜MemberRole.java
 ┃ ┃ ┗ 📜OauthInfo.java
 ┃ ┣ 📂dto
 ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┗ 📜MemberInfoResponse.java
 ┃ ┗ 📂internal
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┗ 📜MemberInfoInternalResponse.java
 ┃ ┃ ┗ 📜MemberInternalController.java
```


### 📂 `domain`
애플리케이션의 핵심 비즈니스 로직과 엔티티들이 위치하는 디렉토리입니다. 
- `admin`: 관리자를 위한 기능을 제공하는 영역
- `auth`: 인증 및 사용자 로그인 관련 로직
- `member`: 사용자와 관련된 데이터 및 서비스 로직을 관리하는 영역
- `common`: 프로젝트 전반에 걸쳐 공유되는 공통 모델 및 유틸리티 클래스가 포함됩니다.

### 📂 `api`
각각의 기능을 제공하는 HTTP 엔드포인트(API)를 정의하는 컨트롤러들이 위치하는 디렉토리입니다.
- `AdminController.java`: 관리자 관련 API를 처리합니다.
- `AuthController.java`: 인증 및 소셜 로그인 API를 처리합니다.
- `MemberController.java`: 회원 관련 API를 처리합니다.

### 📂 `application`
비즈니스 로직을 처리하는 서비스 클래스를 포함하는 디렉토리입니다. 각 서비스는 실제 애플리케이션에서의 데이터 처리와 비즈니스 흐름을 담당합니다.
- `AdminService.java`: 관리자 관련 서비스 로직을 담당합니다.
- `AuthService.java`: 사용자 인증 관련 서비스를 처리합니다.
- `MemberService.java`: 사용자 관련 서비스 로직을 처리합니다.

### 📂 `dao`
데이터베이스와의 상호작용을 담당하는 리포지토리 및 데이터 액세스 객체(DAO)가 위치하는 디렉토리입니다.
- `MemberRepository.java`: 사용자의 데이터를 처리하는 JPA 리포지토리입니다.
- `RefreshTokenRepository.java`: 리프레시 토큰을 저장하는 리포지토리입니다.

### 📂 `dto`
클라이언트와 서버 간의 데이터 전송을 위한 데이터 전송 객체(DTO)가 위치하는 디렉토리입니다. 
- `response`: 응답 DTO 클래스들이 포함됩니다. 
- `request`: 요청 DTO 클래스들이 포함됩니다.

### 📂 `internal`
애플리케이션 내부에서만 사용되는 API와 DTO들이 포함되는 디렉토리입니다. 예를 들어, 내부 시스템에서만 사용되는 사용자 정보를 처리하는 API나 응답 클래스들이 포함됩니다.

### 📂 `common/model`
프로젝트 전반에서 공통으로 사용하는 엔티티나 모델 클래스들이 위치하는 곳입니다.
- `BaseTimeEntity.java`: 모든 엔티티 클래스의 공통 시간을 관리하는 기본 클래스입니다.

## 기술 스택
- Java 17
- Spring Boot 2.7.5
- Spring Data JPA
- JWT (JSON Web Token)
- OAuth 2.0
- MySQL
- Spring Security


