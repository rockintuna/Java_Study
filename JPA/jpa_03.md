## JPA 매핑하기

### 엔티티 매핑

#### 객체와 테이블 매핑

@Entity, @Table : 객체와 테이블 매핑

@Entity가 붙은 클래스는 JPA가 관리하는 엔티티가 된다.  
JPA를 사용하여 테이블과 매핑시 필수적이다.  
기본생성자가 필요하고 final 클래스, enum, interface, inner 클래스를 사용할 수 없다.  
저장할 필드는 final일 수 없다.  

@Table은 엔티티와 매핑할 테이블을 지정한다.(없으면 엔티티 이름으로 매핑)  

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
 - validate : 엔티티와 테이블의 매핑이 정상적인지 검증
 - none : 사용 X

방언(dialect) 설정마다 DDL에 차이가 있음에 유의

#### 필드와 컬럼 매핑

@Column : 필드와 테이블 컬럼 매핑
```
    @Column(name = "name")
    private String name;
```
 - name : 매핑할 테이블 컬럼 이름
 - insertable, updatable : 등록/변경 가능 여부 (default = true)
 - nullable(DDL) : null 허용 여부 (not null constraint)
 - unique(DDL) : unique 제약조건 (제약조건명이 난수로 생성되어 보통 @Table에서 제약조건 생성)
 - columnDefinition(DDL) : 컬럼 정보 직접 입력  
 ex) columnDefinition = "varchar(50) not null"
 - length(DDL) : 문자 길이 제약조건, String에만 사용
 - precision, scale(DDL) : BigDecimal 또는 BigInteger 같은 타입의 자릿수 설정
 
@Enumerated : enum 타입 매핑 정보
```
    @Enumerated(EnumType.STRING)
    private Role role;
```
 - ORDINAL : enum 순서를 숫자로 저장 (문제 발생 소지가 있어 비추)
 - STRING : enum 이름 저장

@Temporal : 날짜 타입 매핑 정보
```
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
```
 - DATE : 날짜만 (컬럼 타입 date)
 - TIME : 시간만 (컬럼 타입 time)
 - TIMESTAMP : 날짜와 시간 (컬럼 타입 timestamp)

LocalDate, LocalDateTime 타입은 애노테이션을 생략할 수 있다. 

@Lob : BLOB,CLOB 타입 등으로 매핑
```
    @Lob
    private String description;
```
필드 타입이 문자면 clob, 나머지는 blob으로 매핑된다.

@Transient : 특정 필드 매핑 안함, 주로 메모리에서만 임시로 어떤 값을 저장하고 싶을 때 사용
```
    @Transient
    private Integer tempId;
```

#### 기본키 매핑

```
    @Id
    @GeneratedValue(strategy = GenarationType.IDENTITY)
    private Long id;
```

@Id : 해당 필드를 기본키로 매핑  

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

IDENTITY 전략의 특징 : 
DB에 insert 되기 전에는 PK 값을 알 수 없다.  
한편 JPA는 커밋 시점에서야 insert 쿼리를 전달하기 때문에 문제가 발생한다.  
그래서 IDENTITY 전략에서만 예외적으로 persist() 시점에 insert 쿼리를 전달한다.  
 
SEQUENCE 전략의 특징 : 
persist() 시점에서 PK 값이 필요하기 때문에 정의된 시퀀스를 참조하는 작업이 수행된다.  
때문에 persist() 마다 DB 접근이 필요한데, 이때의 성능 최적화를 위해 
@SequenceGenerator의 allicationSize(default 50) 속성으로 
DB의 현재 Sequence 값을 미리 설정값 만큼 증가시켜두고
증가된 부분은 추가적인 DB 접근 없이 메모리에서 불러올 수 있다.  
TABLE 전략의 @TableGenerator:allicationSize도 위와 마찬가지이다.  

### 연관관계 매핑 기초
객체지향은 참조가 필요한데 
만약 객체를 연관관계 매핑 없이 테이블에 맞추어 모델링하는 경우 
협력 관계를 만들 수 없다(참조가 불가능하다).  

#### 단방향 연관관계

ex) Member와 Team의 N:1 다대일 관계일때,
Member 입장에서 어노테이션(@ManytoOne)으로 어떤 관계인지 알려줘야 한다.  
특히, DB입장에서는 조인하는 컬럼명이 다른 경우가 많기 때문에 명시해주는 편이다.
```
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
```
Member에서는 Team을 참조할 수 있으나, 
Team에서는 Member를 참조할 수 없는 단방향 관계이다.

#### 양방향 연관관계
Team에서도 Member 목록을 참조할 수 있는 양방향 관계를 만들 수 있다.
```
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
```
테이블 구조는 단방향인 경우와 다르지 않다.  

#### 연관관계의 주인과 mappedBy
테이블 입장에서는 FK 하나로 양방향 연관관계 하나를 가진다. (사실 방향성이 없다.)  
그러나 객체 입장에서는 양방향 연관관계인 경우 두 개의 단방향 연관관계(서로를 참조하는)를 가진다.  

객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 둘이다.  
이 두 관계중 하나를 연관관계의 주인으로 지정해야 한다.  
연관관계의 주인은 외래키를 관리(등록, 수정)하며, 주인이 아닌 쪽은 읽기만 가능하다.  
주인이 아니면 mappedBy로 주인을 지정하고 주인으로 지정되는 관계는 mappedBy를 사용하지 않는다.  

비즈니스 로직에서의 중요성으로 주인을 정하기보다는 FK가 있는 곳을 주인으로 정하자,
그렇지 않으면 A객체에서 변경했는데 B테이블에 update 쿼리가 나가는 등 많이 헷갈리게 된다.

ex) Member와 Team의 N:1 다대일 양방향 관계에서 Member.team이 연관관계의 주인일때
```
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
```
```
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
```

외래 키 등록또는 변경을 할때는 주인을 사용해야 한다.
```
    //정상적인 외래 키 관리
    member.setTeam(team);

    em.persist();
```
위의 경우는 이후에 Team.members로 조회를 할때 영속성 컨텍스트의 1차 캐시를 사용할 경우
추가된 member를 확인할 수 없다.

```
    //가짜 주인을 사용한 비정상적 관리  
    team.getMembers().add(member);

    em.persist(); // TEAM_ID(FK)에 null이 들어간다. 
```

객체지향적 프로그래밍의 순수 객체 상태를 고려해서 양쪽에서 값을 입력해야 한다.  
```
    member.setTeam(team);
    team.getMembers().add(member);

    em.persist();
```
결과는 정상적인 외래 키 값 입력과 다르지 않지만,
이후에 Team.members 조회를 할때 영속성 컨텍스트의 1차 캐시를 사용할 경우에도
추가된 member를 확인할 수 있다.

이를 위해 연관관계 편의 메소드를 활용하자
```
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
```
연관관계 편의 메소드는 주인이 아닌 관계를 가지는 객체에서 만들어도 된다.
그렇지만 양쪽에서 만들어서 사용하지는 말자.

양방향 연관관계에서 무한루프 조심하기
ex) toString(), lombok, JSON 생성 라이브러리  
```
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", team=" + team +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
```
```
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members='" + members + '\'' +
                '}';
    }
```
자동으로 생성된 toString()을 사용할때,
member.toString()에서 team.toString()을 호출하면
team.toString()에서 다시 member.toString()을 호출...

 - Lombok의 @toString 사용하지 않기
 - toString() 구현시 매핑 컬럼 빼기 
 - @Entity를 Json으로 직접 변환하지 않기.(대신 DTO 사용)
 
단방향 매핑만으로 연관관계 매핑은 완료되며, 양방향 매핑은 단순히 역방향 참조를 위한것이다.
때문에 단방향 매핑만 잘 해놓고 양방향 매핑은 필요할 때 추가하면 된다.  
다만, JPQL에서 역방향 참조를 사용할 일이 많다.  

### 다양한 연관관계 매핑

#### 다대일 N:1
@ManyToOne  
가장 많이 사용하는 연관관계이다.  
테이블 입장의 연관관계에서 다(N) 쪽의 테이블이 FK를 가지게 된다.
연관관계의 주인은 다 쪽에 있다.  

ex) Member(N) : Team(1) 의 경우 Member.team이 주인이 됨
```
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
```

양방향인 경우는 주인이 아닌 관계에서 @OneToMany(mappedBy = "필드명") 사용.
```
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
```

#### 일대다 1:N
@OneToMany  
FK는 다(N) 쪽의 테이블에 걸려있지만, 주인은 반대의 관계(1)에서 가지는 연관관계이다.  
@JoinColumn 애노테이션을 꼭 사용해야 하며 사용하지 않으면 조인 테이블(조인용 테이블이 추가로 생성됨) 방식을 사용하게 된다.

ex) Team(1) : Member(N) 의 경우 Team.members가 주인이 됨
```
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();
```

엔티티가 관리하는 외래 키가 다른 테이블에 있고
때문에 관리 작업에서 추가로 Update 쿼리가 발생하는 단점이 있다.  
 => 일대다 단방향보다는 다대일 양방향을 사용하자.
 
일대다 양방향의 경우는 공식적으로는 존재하지 않는 매핑이다.  
주인이 아닌 관계에서 @ManyToOne,
@JoinColumn(name = "필드명", insertable = false, updatable=false)사용하여 구현할 수는 있다.
```
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable=false)
    private Team team;
```

강제로 필드를 주인이지만 읽기 전용으로 생성하여 양방향처럼 사용하는 방법.
 => 그냥 다대일 양방향 사용하자.

#### 일대일 1:1
@OneToOne  
일대일 관계는 그 반대도 일대일이다.  
주 테이블이(Member)나 대상 테이블(Locker) 중에 외래 키를 가질 곳을 선택할 수 있다.
(FK를 어느 테이블에 설정할지는 비즈니스 로직에 따라서...)  
다대일이나 일대다와 테이블 구조에서 다른 점은 외래 키에 unique 제약조건이 추가로 설정된다.  
일대다 관계처럼 FK가 있는 테이블의 반대 엔티티에서 주인으로 설정할 수 없기 때문에 
다대일처럼 FK가 있는 엔티티에서 주인이도록 설정하자.

ex) Member(1) : Locker(1), Member 테이블에 FK가 있다고 가정.
```
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
```

양방향이 필요한 경우 주인이 아닌 엔티티에서 @OneToOne(mappedBy = "필드명") 사용.
```
    @OneToOne(mappedBy = "locker")
    private Member member;
```

양뱡향에서 주의할 점은 프록시 기능의 한계로 인하여
주인이 아닌 관계를 통해 조회 시 지연 로딩이 불가능하다는 단점이 있다.  

#### 다대다 N:M
@ManyToMany  
실무에서 거의 쓰지 않기도 하고 추천하지도 않는 관계.  
RDB같은 경우에 정규화된 테이블 2개로 다대다 관계를 표현할 수 없으며
연결 테이블을 추가하여 일대다, 다대일 관계로 풀어내야 한다.
연결 테이블에서 양쪽 테이블의 PK를 참조하는 FK를 가지고 있다.

ORM의 @ManyToMany는 각 관계와 연결 테이블을 매핑해준다.
@JoinTable(name = "테이블명")으로 연결테이블을 지정.

ex) Member(N) : Product(M)
```
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();
```

양방향이 필요한 경우 주인이 아닌 엔티티에서 @ManyToMany(mappedBy = "필드명") 사용.
```
    @ManyToMany(mappedBy = "products")
    private List<Member> mambers = new ArrayList<>();
```

@JoinTable에 지정된 연결 테이블에서는 추가적인 데이터를 사용할 수 없다는 한계가 있고
쿼리도 생각한 것과 다르게 생성되는 경우가 많기 때문에 사용을 권장하지 않는다.  
 => 연결 테이블용 엔티티 추가
 
연결 테이블 엔티티를 추가(연결 테이블을 엔티티로 승격)하고
@ManyToMany대신 @ManyToOne과 @OneToMany의 조합으로 사용하자.

ex) Member -1:N- MemberProduct -N:1- Product  
Member
```
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
```
MemberProduct
```
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

```
Product
```
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
```

이렇게 하면 @ManyToMany에서는 불가능한 연결 테이블에 속성 추가를 할 수 있다.

### 고급 매핑

#### 상속 관계 매핑
객체에는 상속 관계가 있지만 RDB의 테이블에는 상속 관계가 없다.
그나마 객체 상속과 유사한 것이 슈퍼타입/서브타입 관계 모델링 기법이다.

상속 관계 매핑 : 객체의 상속 구조와 DB의 슈퍼타입/서브타입 관계를 매핑

DB에서 슈퍼타입/서브타입 관계를 물리적인 모델로 구현하는 방법
 - 각각 테이블로 변환 => 조인 전략 
 - 통합 테이블로 변환 => 단일 테이블 전략
 - 서브타입 테이블로 변환 => 구현 클래스마다 테이블 전략

세가지 중 어떤 구현 방식을 사용하더라도 JPA에서 매핑할 수 있다.

상속 관계 매핑
```
@Entity
public class Book extends Item {
    private String author;
    private String isbn;
    ~
}
```
```
@Entity
public class Album extends Item {
    private String artist;
    ~
}
```
```
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
    ~
}
```
객체 상속만 하고 아무런 설정도 하지 않으면 JPA는 기본적으로 단일 테이블 전략을 사용한다.  
그러므로 부모 객체 엔티티에 매핑되는 테이블에 모든 속성이 추가된다.
```
Hibernate: 
    
    create table Item (
       DTYPE varchar(31) not null,
        ITEM_ID bigint not null,
        name varchar(255),
        price integer not null,
        stockQuantity integer not null,
        artist varchar(255),
        author varchar(255),
        isbn varchar(255),
        actor varchar(255),
        director varchar(255),
        primary key (ITEM_ID)
    )
```
단일 테이블 전략은 하위 객체를 구별하기 위한 "DTYPE" 컬럼이 필수이기 때문에 자동으로 생성된다.

전략을 변경하기 위해서는 @Inheritance(strategy = InheritanceType.xxx) 어노테이션을 사용한다.
 - InheritanceType.JOINED : 조인 전략
 - InheritanceType.SINGLE_TABLE : 단일 테이블 전략
 - InheritanceType.TABLE_PER_CLASS : 구현 클래스마다 테이블 전략
  
@DiscriminatorColumn 어노테이션은 "DTYPE" 컬럼(String, 하위 객체명)을 추가한다.
(만약 하위 객체명 대신 다른 값으로 대체하고 싶다면 하위 객체에서 @DiscriminatorValue 사용)
```
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item {
    ~
}
```

조인 전략은 슈퍼타입과 서브타입을 모두 테이블로 생성하며 
서브타입 테이블들은 각각 슈퍼타입 PK를 참조하는 FK를 가지게 된다.
```
Hibernate: 
    
    create table Item (
       ITEM_ID bigint not null,
        name varchar(255),
        price integer not null,
        stockQuantity integer not null,
        primary key (ITEM_ID)
    )
Hibernate: 
    
    create table Album (
       artist varchar(255),
        ITEM_ID bigint not null,
        primary key (ITEM_ID)
    )
Hibernate: 
    
    create table Book (
       author varchar(255),
        isbn varchar(255),
        ITEM_ID bigint not null,
        primary key (ITEM_ID)
    )
Hibernate: 
    
    create table Movie (
       actor varchar(255),
        director varchar(255),
        ITEM_ID bigint not null,
        primary key (ITEM_ID)
    )
Hibernate: 
    
    alter table Album 
       add constraint FK75mrpprv8oigh00y92tibw7id 
       foreign key (ITEM_ID) 
       references Item
Hibernate: 
    
    alter table Book 
       add constraint FK2srbe8wjbanr4vtkrsb8atq7o 
       foreign key (ITEM_ID) 
       references Item
Hibernate: 
    
    alter table Movie 
       add constraint FKqqwswm36y8uqoh9emtoruoxcv 
       foreign key (ITEM_ID) 
       references Item
```
자식 객체를 저장할때(persist()), 
서브타입 테이블 뿐만 아니라 슈퍼타입 테이블에도 insert 된다.  
자식 객체를 조회하면 슈퍼타입/서브타입 테이블을 조인하여 조회한다.  

구현 클래스마다 테이블 전략 적용
```
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    ~
}
```
구현 클래스마다 테이블 전략은 상위 추상 클래스 엔티티에 매핑되는 테이블을 생성하지 않고
하위 클래스를 매핑하는 테이블만 생성한다.  
구현 클래스마다 테이블 전략에서는 @DiscriminatorColumn 어노테이션이 의미가 없다.(사용되지 않는다.)

구현 클래스마다 테이블 전략에서 상위 추상 클래스를 통해 조회할때, 하위 클래스를 모두 조회해야 한다는 단점이 있다.
```
    Item item = em.find(Item.class, movie.getId());
```
```
Hibernate: 
    select
        item0_.ITEM_ID as item_id1_5_0_,
        item0_.name as name2_5_0_,
        item0_.price as price3_5_0_,
        item0_.stockQuantity as stockqua4_5_0_,
        item0_.artist as artist1_0_0_,
        item0_.author as author1_1_0_,
        item0_.isbn as isbn2_1_0_,
        item0_.actor as actor1_7_0_,
        item0_.director as director2_7_0_,
        item0_.clazz_ as clazz_0_ 
    from
        ( select
            ITEM_ID,
            name,
            price,
            stockQuantity,
            artist,
            null as author,
            null as isbn,
            null as actor,
            null as director,
            1 as clazz_ 
        from
            Album 
        union
        all select
            ITEM_ID,
            name,
            price,
            stockQuantity,
            null as artist,
            author,
            isbn,
            null as actor,
            null as director,
            2 as clazz_ 
        from
            Book 
        union
        all select
            ITEM_ID,
            name,
            price,
            stockQuantity,
            null as artist,
            null as author,
            null as isbn,
            actor,
            director,
            3 as clazz_ 
        from
            Movie 
    ) item0_ 
where
    item0_.ITEM_ID=?
```

전략 별 장단점  

조인 전략
 - 장점
    - 테이블 정규화
    - 외래 키 참조 무결성 제약조건 활용 가능
    - 효율적인 저장공간 사용
 - 단점
    - 조인을 통한 조회로 성능저하
    - 복잡한 조회 쿼리
    - 저장 시 insert 2회 호출
    
단일 테이블 전략
 - 장점
    - 조인이 필요 없어서 조회 성능이 좋음
    - 단순한 조회 쿼리
 - 단점
    - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
    - 테이블 하나에 모든 데이터를 저장하기 때문에 조회 성능이 오히려 느려질 수 있음 

구현 클래스마다 테이블 전략 (비추)
 - 장점
    - 서브타입을 명확하게 구분해서 처리할 때 효과적
    - not null 제약조건 사용 가능
 - 단점
    - 여러 자식 테이블을 함께 조회할 때 성능이 느림(union)
    - 자식 테이블을 통합해서 쿼리하기 어려움

#### @MappedSuperClass

### 프록시와 연관관계 관리
