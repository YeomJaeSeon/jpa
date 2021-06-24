# 프로젝션

- 그냥 SELECT로 조회할 칼럼을 지정하는걸 말한다.
- 테이블 대상의 프로젝션은 간단한데 엔티티 대상으로한 jpql에서의 프로젝션은 어떻게할까?

# jpql에서 프로젝션 사용 종류

1. 엔티티 프로젝션

- 지금까지 사용한대로
  `select m from Member m`으로 jpql을 짜면된다.
  alias를 이용해야겠다.(jpql)이닌까

2. 엔티티와 연관된 엔티티 프로젝션

- ? 뭔소리지
- Member 엔티티와 team엔티티가 N : 1연관관계를맺고있다.
- Member엔티티와 연관된 TEam엔티티를 모두 조회할때 사용한다.
- 즉 어떠한 엔티티와 연관된 엔티티를 조회할때 jpql을 어떻게사용할까

`select m.team from Member m`으로 간단하게 사용하면된다.
주의할점은 타입이겠다.(Team.class로 클래스 객체 뽑아오면됨)

- Member 데이터 하나와, Team데이터 두개가 각 테이블에 insert되어있는 상황에서는 Team 엔티티 객체가 두개 조회될것이다.

```java
            List<Team> resultList = em.createQuery("select m.team from Member m", Team.class)
                    .getResultList();

            for (Team team1 : resultList) {
                System.out.println(team1.getName());
            }
```

- 그런데 실제로 나가는 쿼리를 보면 두 테이블이 조인해서 나간다.
- jpql도 sql와 같이 맞춰주는것이 좋다. 나중에 jpql과 sql이다르면 헷갈릴 경우가 많이존재함.
- 즉 실제 sql 예상가능하도록 jpql도 짜자

`"select m.team from Member m join m.team t"`

- jpql에서는 이런식으로 조인 사용하면된다.

- 그런데 조회된 엔티티 객체도 모두 **영속성 컨텍스트에 의해 관리되나??????**

- 대답은 YES

- jpql에 의해 조회된 리스트에 담긴 여러 개의(백개의 엔티티 객체가 조회되면 백개 엔티티 객체모두) 엔티티 객체는 영속성 컨텍스트에 의해 관리가된다.

3. 임베디드 타입 프로젝션

- 엔티티 타입내의 엔베디드 값타입을 프로젝션할땐 어떻게할까

```java
            List<Address> resultList = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
```

- 너무 간단하다.! 이런식으로 `o.address`로 해당 임베디드 값 타입을 지정해주면된다.

4. 스칼라 타입 프로젝션

- 스칼라타입 프로젝션은 엔티티 객체의 필드들을 지정해주면 끝임. 너무간단함

- 그러나 전시간에 TypedQuery, Query할때, 만약 STring과 int타입을 조회하고싶으면 타입지정을 어떻게할까 라는 고민에서 끝났었는데 이번엔 고민을 해결해보자
- 어떻게 타입을 지정할까?
- DTO객체를 하나 생성해서 타입을 지정한다.

- String타입의 username과, int 의 primitive type의 age를 jpql로 조회하고 싶을경우 해당 필드들을 가지고있는 DTO클래스를 하나생성한다.
- 그리고 해당 DTO타입을 넣어주면된다.
- 이때 DTO 클래스의 생성자를 이용하여 해당 객체를 생성하는 방식을 사용하면된다.

```java

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

```

- 이런식으로 조회하고싶은 스칼라타입을 필드로 가지고있는 DTO클래스를 하나 생성한뒤 해당 타입을 지정해주면된다
- 이때, jpql에서 프로젝션할때, DTO클래스의 생성자를 통해 new라는 키워드와함께 사용한다. 주의할건 클래스이름을 패키지이름까지 다 지정해줘야한다.
  (문자열이므로 패키지를 알아서 import못함, 클래스의 fullname을 다 지정해줘야한다.)
- 생성자 타입이라던가, 순서도 모두 일치해야한다.
