# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 41. 사용자 관리     

User 도메인 모델을 추가할것이다.      
User의 속성
String name    
Integer level : 1일반고객,2가게,100관리자,0deactive계정...     
String email

User model
```
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    Long id;

    @NotEmpty
    @Setter
    String email;

    @NotEmpty
    @Setter
    String name;

    @NotNull
    @Setter
    Long level;

    public boolean isAdmin() {
        return level >= 100;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivate() {
        level = 0L;
    }
```

Controller에 실제 계정을 제거하는 대신 deactive하는 기능구현  
```
    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/1004"))
                .andExpect(status().isOk());

        Long id = 1004L;
        verify(userService).deactiveUser(id);
    }
```

Service  
```
@Service
@Transactional //DB에 Transaction처리를 위함이다
               //안해주면 update/delete 안되었음
public class UserService {

~

    public User updateUser(Long id, String email, String name, Long level) {
        //TODO 예외처리 추가 필요
        User user = userRepository.findById(id).orElse(null);
        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);
        return user;
    }

    public User deactiveUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.deactivate();
        return user;
    }

}
```
    
패스워드, 회원가입등은 추후 추가 예정   
    
    