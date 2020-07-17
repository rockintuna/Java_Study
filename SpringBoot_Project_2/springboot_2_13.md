# 스프링 부트 프로젝트
### 지인 정보 관리 시스템 만들기  

#### 13. JPA Query Method

일반적으로 우리가 테스트에서 자주 사용하는 JPA의 findall()은 실제 운영에서는 사용하지 않는다.  
그 대신 where절이 걸려있는 sql을 원하게 되는데 이때 JPA Query Method를 사용하여 쿼리를 만들어 쓸 수 있다.  
find : select
By : where
And : and
Or : or
Between : between A and B
LessThan : tab.col < val
LessThanEqual : tab.col <= val
GreaterThan : tab.col > val
GreaterThanEqual : tab.col >= val
After : tab.date > val(date)
Before : tab.date < val(date)
IsNull : is null, = null
(Is)NotNull : not null, != null
Like : like %val%
NotLike : not like %val% 
StartingWith : like val%
EndingWith : like %val
Containing : like %val%
OrderBy : order by
Not : <>
In : in ()
NotIn : not in ()
True : = true
False : = false
IgnoreCase : UPPER(tab.col) / UPPER(val)

ex)  
만약 무조건 1 row만 리턴하는 쿼리라면 리턴타입을 Person으로 해도 되지만 2 rows 이상 리턴할 때 에러가 발생한다.  
 
이름으로 찾기 : List<Person> findByName(String name)  
이름 포함 : List<Person> findByNameContaining(String name) / List<Person> findByNameLike(String name)    
차단되지 않은 사람 : List<Person> findByBlockIsNull()  
특정 기간동안에 생일이 있는 사람 : List<Person> findByBirthDayBetween(LocalDate startDate, LocalDate endDate);

만약 이번달에 생일인 사람을 찾는 기능을 추가하고 싶을때
LocalDate에서 월만 분류하면 성능적으로 바람직하지 못함.
그렇기 때문에 BirthDay라는 Entity를 추가하여 생일의 연월일을 나눈다.
```
package com.fastcampus.javaallinone.project3.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable //Entity에 속할 DTO라는것을 명시
@Data
@NoArgsConstructor
public class Birthday {
    private int yearOfBirthday;

    @Min(1)
    @Max(12)
    private int monthOfBirthday;

    @Min(1)
    @Max(31)
    private int DayOfBirthday;

    //여기서 생성자를 따로 만들었는데 LocalDate 타입을 사용해서 날짜 제한을 두기 위함임.
    public Birthday(LocalDate birthday) {
        this.yearOfBirthDay = birthday.getYear();
        this.monthOfBirthDay = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    };
}
```

Person의 BitrhDay 속성 변경
```
~
    @Valid
    @Embedded //Embeddable DTO 사용
    private BirthDay birthDay;
~
```

Person Repository에 기능 추가
@Query는 JPQL이라는 Entity기반으로 쿼리를 실행하는 로직을 이용할 어노테이션이다.  
아래에서 사용하지 않으면 Person에는 monthOfBirthDay라는 속성이 없으므로 에러가 발생한다.  
아래 JPQL문의 ?1은 메서드의 첫번째 인자를 의미한다.   
```
@Query(value = "select person from Person person where person.birthDay.monthOfBirthday = ?1")
List<Person> findByMonthOfBirthday(int monthOfBirthday);
```

명시적인 파라미터를 사용하고 싶다면 아래와 같이 사용할 수 있다.
```
@Query(value = "select person from Person person where person.birthDay.monthOfBirthday = :monthOfBirthDay and person.birthDay.dayOfBirthday = :dayOfBirthday")
List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday,@Param("dayOfBirthday") int dayOfBirthday);
```

실제 DB에서 수행될 SQL문 그대로를 만들고 싶을때는 nativeQuery 옵션을 사용할 수 있다.
```
@Query(value = "select * from person where month_of_birthday = :monthOfBirthday and day_of_birthday = :dayOfBirthday", nativeQuery = true)
List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday,@Param("dayOfBirthday") int dayOfBirthday);
```
   
test 디렉토리에 resources 디렉토리 생성 후 data.sql 파일 생성
```
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (1,'martin',10,'A',1992,1,30);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (2,'david',9,'B',1994,3,24);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (3,'dennis',8,'AB',2000,12,24);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (4,'sophia',7,'O',1999,8,15);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (5,'benny',6,'A',2001,2,24);

insert into block(`id`, `name`) values (1, 'dennis');
insert into block(`id`, `name`) values (2, 'sophia');

update person set block_id = 1 where id = 3;
update person set block_id = 2 where id = 4;
```
테스트 진행 시 위의 SQL문을 먼저 실행한 뒤 테스트가 진행된다.  
    
    