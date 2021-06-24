# jpql 왜쓴다고?

- 엔티티 객체를 대상으로 하는 작업하는 jpa에서 엔티티 객체를 대상으로 쿼리를 날리기 위해서 사용한다
- 쿼리 검색은 꼭필요하다. 즉, jpa에서 사용하는 쿼리검색으로 **객체 지향 쿼리 언어**라 할수있다.

- jpql은 sql을 추상화하여 특정 DB에 의존하지않는다.
- 결국 DB에 SQL을 날려야하므로 jpql도 sql로 변환된다.

# jpql문법

- 표준 sql이랑 똑같다.

`select ~ from ~ where ~ groupby ~ having ~ order by ~`

- 그런데 jpql은 엔티티 객체를 대상으로 쿼리를 날린다. 그러므로 엔티티 이름(기본은 클래스이름이다. 변경하려면 `@Entitiy(name="Hello")`이런식으로 변경해야함)과 엔티티내의 필드의 이름을 꼭 맞춰야한다. 테이블의 이름고 테이블의 칼럼 이름에 맞추는게아니다. 대소문자까지 꼭맞춰야한다.(자바)

`select m.username from Member as m`

- jpql키워드 (select, from ... )이런것들은 대소문자 상관X

- alias는 무조건넣어야한다. as는 생략가능

- 집계함수들 모두 사용가능
  sum()
  MAX()
  AVG()
  ..

# typedQuery와 Query

- 반환타입을 지정하면 TypedQuery(타입 명확해야 지정가능하겠다)
- 반환타입 지정안하면Query(타입 명확하지않을때)

`em.createQuery("select m.username, m.age from Member m")`
이렇게 String인 username과 int인 age를 뽑을땐 타입을 지정할수가없음. 반환타입이 Query이다

```java
List<Integer> resultList1 = em.createQuery("select m.age from Member m", Integer.class)
                    .getResultList();
            for (Integer integer : resultList1) {
                System.out.println("integer = " + integer);
            }
```

- 이렇게 타입을 지정할수있을떄 typedQuery를 반환한다.

# 결과조회 api

1. `getResultList()`

- List를 반환
- 아무것도없으면 null반환
- 꽤나 안전하다

2. `getSingleResult()`

- 무조건 하나만 반환
- 아무것도없어도 두개이상이여도 예외터짐
- 쓰지말자

# 파라미터 바인딩

1. 이름 기준

```java
            Integer singleResult = em.createQuery("select m.age from Member m where m.username = :username", Integer.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
```

- where문 다음에 `:username`으로 파라미터를 지정하고 `setParameter()` api를 이용해서 해당 파라미터에 값을 넣는다.

2. 위치기준(쓰지말자)

```java
            Integer singleResult = em.createQuery("select m.age from Member m where m.username = ?2", Integer.class)
                    .setParameter(2, "member1")
                    .getSingleResult();
```

- where문 다음에 `?1`로 위치를 지정하고 `setParameter()` api로 해당 위치에 값을 넣는다.
- 위치가 바뀌면 잘못된 파라미터에 값을 넣을 위험이있음
- URL와 URN의 차이와 같네요.

끝!
