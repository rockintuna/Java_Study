## JPA 맵핑하기

### 엔티티 맵핑

#### 객체와 테이블 맵핑

@Entity, @Table : 객체와 테이블 맵핑

@Entity가 붙은 클래스는 JPA가 관리하는 엔티티가 된다.  
JPA를 사용하여 테이블과 맵핑시 필수적이다.  
기본생성자가 필요하고 final 클래스, enum, interface, inner 클래스를 사용할 수 없다.  
저장할 필드는 final일 수 없다.  

@Table은 엔티티와 맵핑할 테이블을 지정한다.(없으면 엔티티 이름으로 맵핑)  

```
@Entity
@Table(name = "Member")
public class Account {

    @Id
    private Long id;

    ...
}
```

#### 데이터베이스 스키마 자동 생성

애플리케이션 실행 시점에 해당 데이터베이스에 적절한 DDL을 자동으로 수행하여 테이블을 생성하는 기능.  
개발 단계에서 유용하게 사용할 수 있다.

```
<property name="hibernate.hbm2ddl.auto" value="create"/>
```

hibernate.hbm2ddl.auto
 - create : 기존 테이블 삭제 후 다시 생성
 - create-drop : 시작 시점에 테이블 생성 후 종료 시점에 제거
 - update : 스키마 변경분만 반영 (컬럼 삭제는 안되는 것에 주의)
 - validate : 엔티티와 테이블의 맵핑이 정상적인지 검증
 - none : 사용 X

방언(dialect) 설정마다 DDL에 차이가 있음에 유의

#### 필드와 컬럼 맵핑

@Column : 필드와 테이블 컬럼 맵핑
```
    @Column(name = "name")
    private String name;
```
 - name : 맵핑할 테이블 컬럼 이름
 - insertable, updatable : 등록/변경 가능 여부 (default = true)
 - nullable(DDL) : null 허용 여부 (not null constraint)
 - unique(DDL) : unique 제약조건 (제약조건명이 난수로 생성되어 보통 @Table에서 제약조건 생성)
 - columnDefinition(DDL) : 컬럼 정보 직접 입력  
 ex) columnDefinition = "varchar(50) not null"
 - length(DDL) : 문자 길이 제약조건, String에만 사용
 - precision, scale(DDL) : BigDecimal 또는 BigInteger 같은 타입의 자릿수 설정
 
@Enumerated : enum 타입 맵핑 정보
```
    @Enumerated(EnumType.STRING)
    private Role role;
```
 - ORDINAL : enum 순서를 숫자로 저장 (문제 발생 소지가 있어 비추)
 - STRING : enum 이름 저장

@Temporal : 날짜 타입 맵핑 정보
```
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
```
 - DATE : 날짜만 (컬럼 타입 date)
 - TIME : 시간만 (컬럼 타입 time)
 - TIMESTAMP : 날짜와 시간 (컬럼 타입 timestamp)

LocalDate, LocalDateTime 타입은 애노테이션을 생략할 수 있다. 

@Lob : BLOB,CLOB 타입 등으로 맵핑
```
    @Lob
    private String description;
```
필드 타입이 문자면 clob, 나머지는 blob으로 맵핑된다.

@Transient : 특정 필드 맵핑 안함, 주로 메모리에서만 임시로 어떤 값을 저장하고 싶을 때 사용
```
    @Transient
    private Integer tempId;
```

#### 기본키 맵핑

```
    @Id
    @GeneratedValue(strategy = GenarationType.IDENTITY)
    private Long id;
```

@Id : 해당 필드를 기본키로 맵핑  

@GeneratedValue : 기본키 값 자동 생성
 - IDENTITY : DB에 위임  
 MySQL, PostgreSQL 등에서 사용
 - SEQUENCE : DB 시퀀스를 생성 후 사용  
 Oracle, PostgreSQL 등에서 사용  
 @SequenceGenerator를 이용하여 사용할 시퀀스 커스텀 가능
 - TABLE : 키 생성용 테이블 생성  
 어느 DB에서나 사용할 수 있지만 성능 이슈 있음  
 @TableGenerator를 이용하여 사용할 테이블 커스텀 가능
 - AUTO : 방언에 따라 자동 지정


### 연관관계 맵핑 기초

#### 단방향 연관관계

