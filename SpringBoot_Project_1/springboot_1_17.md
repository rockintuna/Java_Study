# 스프링 부트 프로젝트
### 레스토랑 예약 사이트 만들기 

#### 17. 프론트 엔드     

webpack / webpack-cli / webpack-dev-server 설치
```shell script
npm install -save-dev webpack webpack-cli webpack-dev-server
```

start 추가
```
{
  "name": "eatgo-web",
  "version": "1.0.0",
  "description": "EatGo Web Project",
  "main": "src/index.js",
  "scripts": {
    "start": "webpack-dev-server --port 3000",
    "test": "jest"
  },
  "author": "Jeong In",
  "license": "ISC",
  "devDependencies": {
    "webpack": "^4.43.0",
    "webpack-cli": "^3.3.11",
    "webpack-dev-server": "^3.11.0"
  }
}
```

webpack-dev-server 실행
```shell script
npm start
```
실제로는 아래와 같은 프로세스가 실행되어 있다   
```
ps -ef | grep webpack
                                          
501 43162 43161   0 11:28PM ttys000    0:03.58 node /Users/ijeong-in/Git_repo/eatgo/eatgo-web/node_modules/.bin/webpack-dev-server --port 3000```
```

index.html
webpack-dev-server가 index.js를 main.js로 바꿔준다.
```
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>EatGo</title>
</head>
<body>
    <div id="app"></div>
    <script src="./main.js"></script>
</body>
</html>
```


index.js
app을 가져와서 내용 고치기    
모든 가게의 id, 이름, 주소 출력    
```
(async () => {
    const url = 'http://localhost:8080/restaurants';
    const response = await fetch(url);
    const restaurants = await response.json();

    const element = document.getElementById('app');
    element.innerHTML = `
        ${restaurants.map(restaurant => `
        <p>
            ${restaurant.id}
            ${restaurant.name}
            ${restaurant.address}
        </p>
        `).join('')}
    `;
})();
```


별도의 웹서버에서 CORS에 따라 서로 보안을 위해 접근할 수 없기때문에 
Spring에서 CrossOrigin annotation 처리
```
@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {

        List<Restaurant> restaurants = restaurantService.getRestaurants(); //API들의 중복제거를 위해 가게 저장소 사용
        return restaurants;
    }
```
    
    