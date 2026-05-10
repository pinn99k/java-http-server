# java-http-server

> HTTP server built from scratch with Java ServerSocket — no Spring, no Tomcat  
> Spring도 Tomcat도 없이 ServerSocket으로 HTTP 서버를 직접 구현한 도서관 관리 웹 애플리케이션

---

## Why I Built This

Tomcat이 내부에서 어떻게 동작하는지 이해하고 싶었습니다.  
같은 네트워크에서 여러 대의 컴퓨터가 동시에 접속하는 것을 목표로, 프레임워크 없이 ServerSocket으로 직접 구현했습니다.

---

## Architecture

```
Client (Browser)
        │
        ▼
HttpServer (ServerSocket, port 12345)
        │  ← accept() loop · thread pool (size 10)
        ▼
HttpRequestHandlerV4 (Runnable)
        │
        ▼
HttpRequest / HttpResponse  ← HTTP parsing & response
        │
        ▼
ServletManager  ← path-based routing (/  /user  /admin)
        │
        ▼
Controller → CommandManager → Command  ← action-based routing (?action=)
        │
        ▼
LibraryService
        ├── LibraryMap        (in-memory store)
        └── LibraryStorage    (file I/O: users.txt / books.txt / borrow.txt)
```

---

## Key Implementation

### ServerSocket accept loop

```java
ServerSocket serverSocket = new ServerSocket(12345);
ExecutorService threadPool = Executors.newFixedThreadPool(10);

while (true) {
    Socket socket = serverSocket.accept();
    threadPool.submit(new HttpRequestHandlerV4(socket, servletManager));
}
```

### Two-level routing: path → action

```java
// 1단계: path 기반 컨트롤러 분기
HttpServlet servlet = servletManager.find(request.getPath());

// 2단계: ?action= 기반 커맨드 분기
String action = request.getParam("action");
commandMap.get(action).execute(request, response);
```

### UUID session (URL-based)

```java
String sessionId = UUID.randomUUID().toString();
sessionStore.put(sessionId, username);
response.sendRedirect("/user?sessionId=" + sessionId);
```

> 쿠키 파싱 미구현으로 세션 ID를 URL 쿼리 파라미터로 전달.  
> Known limitation: 세션 ID가 URL에 노출됨.

---

## Features

- 로그인 / 로그아웃 (UUID session)
- 도서 목록 조회, 대출, 반납, 연체 확인
- 관리자 전용: 도서 추가, 회원 추가
- 종료 시 파일 저장 (`STOP`)

---

## Getting Started

### Local

1. IntelliJ IDEA에서 프로젝트 열기
2. `ServerMainV4.java` 실행
3. 브라우저에서 `http://localhost:12345` 접속

### LAN (같은 Wi-Fi에서 여러 대 접속)

1. 서버 PC에서 로컬 IP 확인
   ```
   ipconfig
   ```
2. `ServerMainV4.java` 실행
3. 다른 PC 브라우저에서 `http://<서버IP>:12345` 접속

   접속이 안 될 경우 방화벽에서 포트 허용:
   ```powershell
   netsh advfirewall firewall add rule name="LibraryServer" dir=in action=allow protocol=TCP localport=12345
   ```

Default accounts:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | 123      | Admin |
| jack     | 456      | User  |
| yop      | 789      | User  |

---

## What I Learned

- **ServerSocket** — 클라이언트 연결을 소켓 레벨에서 직접 받고 처리하는 방식
- **Session** — UUID로 로그인 상태를 유지하는 방식 (프레임워크 없이)
- **Threading** — 요청마다 스레드를 분리해야 동시 접속이 가능하다는 것
- **Class design** — 처음엔 한 곳에 다 넣다가 기능별로 분리하면서 왜 나눠야 하는지 이해

---

## Known Limitations

- 공유 자원 동기화 미적용 (concurrent access 처리 없음)
- 비밀번호 평문 저장
- 세션 ID가 URL에 노출됨 (쿠키 미구현)
