# 스프링 프로젝트
### 스프링 프레임워크 실습 및 프로젝트    

#### 01. 스프링 학습 전 필요지식  

 - maven  
 
build를 담당하는 maven & gradle  
mvc compile을 통해 필요한 library들을 검토하고 자동으로 다운로드 해준다.
다운받은 jar파일들은 User Home 디렉토리의 .m2/repository에 보관된다.    
설정 파일  
maven : pom.xml  
gradle : build.gradle  
컴파일하면 target으로 실행가능하도록 생성됨  

 - logback (slf4j) 

level이 내려갈수록 log 범위가 작아짐      
logging을 담당하는 logback    
.trace  
.debug  
.info  
.warn  
.error  

logback에 대한 설정은 src/main/resources에 logback.xml(spring-boot에서는 logback-spring.xml)을 생성하여 할 수 있다.    
ex) logback.xml
```
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="trace">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

 - DB(h2)  
 
설치
```
brew install h2
```
실행 (브라우저에서 h2-console로 연결된다)
```
h2
```

JDBC API는 java.sql과 javax.sql이 있는데 각각 JAVA SE와 EE에 호환된다.  

연결, 테이블생성, 조회
```
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Hello World");

        //Connection, Statement, ResultSet은 java.sql에 있는 구현체이다.
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:mem:test;MODE=MySQL;";
            connection = DriverManager.getConnection(url, "sa", "");
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            //테이블 생성
            statement.execute("create table member(" +
                    "id int auto_increment, " +
                    "username varchar(255) not null, " +
                    "password varchar(255) not null, " +
                    "primary key(id))");

            //데이터 insert
            statement.executeUpdate("insert into member(username, password)" +
                    "values('jilee','1234')");

            ResultSet resultSet = statement.executeQuery("select * from member");
            
            //select 결과는 ResultSet에 저장되며
            //ResultSet.next()는 커서를 레코드 단위로 리턴한 뒤 마지막에는 false를 리턴한다. 
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                logger.info(id + username + password);
            }

            connection.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        //사용한 자원 정리 단계
        } finally { 
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                    throwables.printStackTrace();
            }
        }
    }
}
```

리팩토링 후 
var 사용 / try with resource 사용 / Member 모델 사용
```
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        logger.info("Hello World");

        Class.forName("org.h2.Driver");
        var url = "jdbc:h2:mem:test;MODE=MySQL;";

        try(var connection = DriverManager.getConnection(url, "sa", "");
            var statement = connection.createStatement();)
        {
            connection.setAutoCommit(false);
            statement.execute("create table member(" +
                    "id int auto_increment, " +
                    "username varchar(255) not null, " +
                    "password varchar(255) not null, " +
                    "primary key(id))");

            try {
                statement.executeUpdate("insert into member(username, password)" +
                        "values('jilee','1234')");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }

            var resultSet = statement.executeQuery("select * from member");
            while (resultSet.next()) {
                var member = new Member(resultSet);
                logger.info(member.toString());
            }

        } catch (SQLException e) {
        }
    }
}
```
 - Lombok  
 
코드의 가독성을 위하여 사용한다.  
```
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Member {

    private int id;
    @NonNull private String userName;
    @NonNull private String password;

    public Member(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("id");
            this.userName = resultSet.getString("username");
            this.password = resultSet.getString("password");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
```
 
 - servlet  
   
EE 버전의 스팩을 구현한 servlet 서버로는 tomcat / jeus / oracle weblogic 등이 있다.  
  
EE 사용을 위한 의존성 추가
```
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
```

WAR 설정 추가
```
    <groupId>com.mycompany.app</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
```



