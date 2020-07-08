# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 62. API 한번에 실행하기   

MySQL DB를 사용할것임  
Container 기술을 활용하여 하나의 컴퓨터에서 가상화된 여러 개의 Container를 올려 여러 서버를 동시에 실행 (Docker)
Docker Compose를 활용  

build.gradle 의존성 추가
```
dependencies {
~
	implementation 'mysql:mysql-connector-java'
~
```

application.yml datasource 추가
```
---

spring:
  profiles: mysql
  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:3306/${MYSQL_DATABASE}
    username: ${MYSQL_USER}
    password: ${MYSQL_PASSWORD}
```

docker-compose.yml 생성  
각 서비스 등록  
```
version: '3'
services:
  mysql:
    image: mariadb:10.4.7
    env_file: .env
    volumes:
      - ./data/mariadb:/var/lib/mysql
    ports:
      - 13306:3306
    command: |
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_unicode_ci
    healthcheck:
      test: ["CMD", "mysqladmin" ,"ping", "-h", "localhost"]
      timeout: 10s
      retries: 10
  login-api:
    image: openjdk:11
    env_file: .env
    volumes:
      - ./run.sh:/Users/ijeong-in/Git_repo/eatgo/run.sh
      - ./eatgo-login-api/build/libs:/Users/ijeong-in/Git_repo/eatgo/libs
    ports:
      - 8001:8080
    depends_on:
      - mysql
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && sh run.sh"
  admin-api:
    image: openjdk:11
    env_file: .env
    volumes:
      - ./run.sh:/Users/ijeong-in/Git_repo/eatgo/run.sh
      - ./eatgo-admin-api/build/libs:/Users/ijeong-in/Git_repo/eatgo/libs
    ports:
      - 8002:8080
    depends_on:
      - mysql
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && sh run.sh"
  customer-api:
    image: openjdk:11
    env_file: .env
    volumes:
      - ./run.sh:/Users/ijeong-in/Git_repo/eatgo/run.sh
      - ./eatgo-customer-api/build/libs:/Users/ijeong-in/Git_repo/eatgo/libs
    ports:
      - 8003:8080
    depends_on:
      - mysql
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && sh run.sh"
  restaurant-api:
    image: openjdk:11
    env_file: .env
    volumes:
      - ./run.sh:/Users/ijeong-in/Git_repo/eatgo/run.sh
      - ./eatgo-restaurant-api/build/libs:/Users/ijeong-in/Git_repo/eatgo/libs
    ports:
      - 8004:8080
    depends_on:
      - mysql
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && sh run.sh"
  admin-web:
    image: node:10.16.3
    volumes:
      - ./eatgo-admin-web:/Users/ijeong-in/Git_repo/eatgo
    ports:
      - 8082:3000
    environment:
      - VUE_APP_API_BASE_URL=http://localhost:8002
      - VUE_APP_LOGIN_API_BASE_URL=http://localhost:8001
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && npm run serve"
    healthcheck:
      test: curl -sS http://localhost:8080 || exit 1
      timeout: 10s
      retries: 10
  customer-web:
    image: node:10.16.3
    volumes:
      - ./eatgo-customer-web:/Users/ijeong-in/Git_repo/eatgo
    ports:
      - 8083:3000
    environment:
      - VUE_APP_API_BASE_URL=http://localhost:8003
      - VUE_APP_LOGIN_API_BASE_URL=http://localhost:8001
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && npm run serve"
    healthcheck:
      test: curl -sS http://localhost:8080 || exit 1
      timeout: 10s
      retries: 10
  restaurant-web:
    image: node:10.16.3
    volumes:
      - ./eatgo-restaurant-web:/Users/ijeong-in/Git_repo/eatgo
    ports:
      - 8084:3000
    environment:
      - VUE_APP_API_BASE_URL=http://localhost:8004
      - VUE_APP_LOGIN_API_BASE_URL=http://localhost:8001
    command: bash -c "cd /Users/ijeong-in/Git_repo/eatgo && npm run serve"
    healthcheck:
      test: curl -sS http://localhost:8080 || exit 1
      timeout: 10s
      retries: 10
```

DB 및 API 서비스 실행시 적용되는 환경설정 파일(.env)
```
MYSQL_ROOT_PASSWORD=password
MYSQL_USER=root
MYSQL_PASSWORD=password
MYSQL_DATABASE=eatgo
MYSQL_HOST=mysql

SPRING_PROFILES_ACTIVE=mysql
```

각 API 서비스에서 jar파일을 실행할 run.sh
```
until java -jar libs/*.jar
do
  printf "******************************************************************"
  sleep 5
done
```

docker-compose를 통하여 묶어서 단계적으로 실행
```shell script
$ docker-compose up
```

Docker Container list 확인
```shell script
$ docker-compose ps
         Name                       Command                State     Ports
--------------------------------------------------------------------------
eatgo_admin-api_1        bash -c cd /Users/ijeong-i ...   Exit 137        
eatgo_admin-web_1        docker-entrypoint.sh bash  ...   Exit 0          
eatgo_customer-api_1     bash -c cd /Users/ijeong-i ...   Exit 137        
eatgo_customer-web_1     docker-entrypoint.sh bash  ...   Exit 0          
eatgo_login-api_1        bash -c cd /Users/ijeong-i ...   Exit 137        
eatgo_mysql_1            docker-entrypoint.sh --cha ...   Exit 0          
eatgo_restaurant-api_1   bash -c cd /Users/ijeong-i ...   Exit 137        
eatgo_restaurant-web_1   docker-entrypoint.sh bash  ...   Exit 0   
```
    
    