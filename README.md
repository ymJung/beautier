# block tx beautier


    Request : 
        GET /gasPrice

    Response :
        tx - gas price (avg, max, min)  // UNIT : Gwei
        gas price 가격 단위로 그룹핑, 오름차순 tx 수를 함께 제공



### 개발환경
- java8 / spring-boot(embedded tomcat) / maven
- mac, intellij/vscode, git 

### 구동조건
- application.properteis 에 infura 에서 받은 network.key 가 있어야함.
- 인터넷 연결 환경이 필요
- git master branch 의 BeautierApplication 를 구동(Run)한다.

### 사용방법
- /gasPrice 로 기본기능 사용
    - price (avg, min, max) 단위: Gwei
    - price 단위 그룹핑, 트렌젝션 수를 제공
    
- local 또는 설치한 서버의 설정한 포트 접속 (8090)
- DEFAULT - 기본 기능 제공 (최신블록)
- PRETTY - 위 기본기능의 포맷팅된 json 을 제공
- CUSTOM - 블록을 지정 기능, price 오름차순/내림차순 기능 제공
 