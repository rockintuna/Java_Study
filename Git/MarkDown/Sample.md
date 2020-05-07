# Markdown 작성법

### 1. 헤더 Headers (#)
```
# 헤더1
## 헤더2
### 헤더3
#### 헤더4
##### 헤더5
```
# 헤더1
## 헤더2
### 헤더3
#### 헤더4
##### 헤더5

### 2. 블럭쿼트 (>)
```
> 블럭쿼트
> > 블럭쿼트 2
> > > 블럭쿼트 3
```
> 블럭쿼트
> > 블럭쿼트 2
> > > 블럭쿼트 3

### 3.1. 목록 (순서)
```
1. 첫번째
2. 두번째
3. 세번째
```
1. 첫번째
2. 두번째
3. 세번째

### 3.2. 목록(순서 X)
```
* 대
    * 중
        * 소
```
* 대
    * 중
        * 소
        
### 4.1. 코드 (TAB)
```
This is a normal paragraph:

    This is a code block.    
end code block.
```
This is a normal paragraph:

    This is a code block.    
end code block.

### 4.2. 코드 (코드블럭)
* \<pre>\<code> ~ \</code>\<\pre>
```
<pre>
<code>
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Java");
  }

}
</code>
</pre>
```
<pre>
<code>
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Java");
  }

}
</code>
</pre>

* \``` ~ ```
<pre>
<code>
```
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Java");
  }

}
```
</code>
</pre>

```public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Java");
  }

}
```

### 5. 수평선
```
* * *

***

*****

- - -

---------------------------------------
```
* * *

***

*****

- - -

---------------------------------------

### 6. 링크
* 참조 링크
```
[link keyword][id]

[id]: URL "Optional Title here"

// code
Link: [Google][googlelink]

[googlelink]: https://google.com "Go google"
```
Link: [Google][googlelink]

[googlelink]: https://google.com "Go google"

* 외부 링크
```
Link : [Google](https://google.com, "google link")
```
Link : [Google](https://google.com, "google link")

* 자동연결
```
일반적인 URL 혹은 이메일주소인 경우 적절한 형식으로 링크를 형성한다.

* 외부링크: <http://example.com/>
```
* 외부링크: <http://example.com/>

### 7. 강조
```
* *기울기*
* **굵게**
* ***굵게***
* __굵게__
* ~~취소선~~
```
* *기울기*
* **굵게**
* ***굵게***
* __굵게__
* ~~취소선~~

### 8. 이미지
```
![Alt text](205_Markdown_logo_logos-512.png)
```
![Alt text](205_Markdown_logo_logos-512.png)

### 9. 줄바꿈
```
* 줄 바꿈을 하기 위해서는
줄 마지막에서 2칸이상을 띄어쓰기해야 한다.

* 줄 바꿈을 하기 위해서는"띄어쓰기x2" 
줄 마지막에서 2칸이상을 띄어쓰기해야 한다.
```

* 줄 바꿈을 하기 위해서는
줄 마지막에서 2칸이상을 띄어쓰기해야 한다.

* 줄 바꿈을 하기 위해서는  
줄 마지막에서 2칸이상을 띄어쓰기해야 한다.