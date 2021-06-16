# 테이블들의 공통의 필드들 매핑할때 사용

- `@MappedSuperclass`는 테이블들이 공통으로 사용하는 필드들을 묶을때 사용한다.
- 즉, 공통의 속성을 매핑할때 사용한다.

## 상황

- 테이블A필드
  -> CREATE\*TIME VARCHAR /\*\**
  -> CREATE*USER VARCHAR /\*\*\*
  -> id bigint
  -> isbn varchar
  ->...

- 테이블 B필드
  -> CREATE\*TIME VARCHAR /\*\**
  -> CREATE*USER VARCHAR /\*\*\*
  -> id bigint
  -> author varchar
  -> ..

- 두테이블은 공통적으로 생성한 시간과 생성한 유저라는 필드를 사용한다.
- 두테이블 매핑한 엔티티들에서 두 필드를 매핑하기위해서 멤버변수를 적는게 너무 귀찮을때 사용..
- 중복되어있으므로 뭔가 좋지도않다.!!
- 그래서 두 엔티티의 부모클래스로 `@MappedSuperclass`를 사용한다.

```java

@MappedSuperclass
public abstract class Base{
  @Column(name = "CRATE_TIME")
  private LocalDateTime createdDateTime;
  @Column(name = "CREATE_USER")
  private String createdUser;
}
```

- 이 클래스를 테이블 A와 테이블 B를 매핑한 두 엔티티가 상속받으면 된다.
- `@MappedSuperclass`를 사용하는 클래스는 직접 객체를 생성할일이없으므로 abstract 제어자를 사용한다.

## 결과

- Base 클래스는 테이블과 매핑하는게 아니므로(`@Entity`가 아니라 `@MappedSuperclass`이므로) jpa실행될때 테이블이 생성되지않는다.
- 테이블 A에 매핑한 엔티티 A와 테이블 B에 매핑한 엔티티 B에는 필드를 매핑한 멤버변수가 없어도 테이블 생성시 공통의 필드가 생성된다.(`@MappedSuperclass`의 클래스를 상속받았기에 가능함.)
- 중복되는 코드도 없고 관리도 쉬워짐!!

## 참고

- `@Entitiy`에너테이션이 붙은 클래스는 `@Entity`에너테이션이 붙은 클래스를 상속받거나 `@MappedSuperclass` 에너테이션이 붙은 클래스를 상속받아야한다. 전자면 상속관계 매핑일테고, 후자면 공통의 속성을 매핑할 뿐이다.
