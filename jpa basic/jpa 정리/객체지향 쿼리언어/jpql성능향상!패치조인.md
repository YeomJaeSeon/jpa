# 패치조인

- 회원과 연관된 팀에 대한 정보도 모두 조회해야한다.
- 회원엔티티와 팀엔티티를 모두조회해야한다.(현재는 LAZY 로딩상태)
- jpql을 이용해서 조회해보자.

```java
            String query = "select m from Member m";

            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member o : result) {
                System.out.println("o = " + o.getUsername() + ", team : " + o.getTeam().getName());
            }
```

- 반복문을 돌면서 프록시객체인 team을 통해서 DB로 조회쿼리가나간다.
- 이러면 1 + N 문제가 발생한다. 한번 실해한 쿼리에 대해서 각 엔티티가 연관된 쿼리를 조회하는 문제.. 한번에 한방쿼리로 다 가져올순없을까?
- 근데 이부분에서 궁금했던건 지연로딩으로 설정해서 프록시 객체로 받아와서그렇게된줄알고 EAGER로 바꾸어보았다. EAGER로 바꾸면 프록시 객체를 반환해서 실제 엔티티 사용할대 쿼리가 나가는거 빼곤 똑같이 1+N 문제가발생한다. jpql에서 어떠한 엔티티에대한 연관된 엔티티의 값을 조회할땐 항상 1+N문제가 발생..
- 패치조인을 이용해보자

`select m from Member m join fetch m.team`

- 이렇게 패치조인을 이용하면 한방쿼리가 나간다. 1+N 문제가 발생하지않으니 DB성능도 괜찮아졌다.

다대일에서의 연관된 엔티티조회할때는 이렇지만 다대일에서 연관된 엔티티를 가지고있는 컬렉션을 조회할땐 패치조인을 어떻게사용할까?

- 사용법은 똑같다
  `select t from Team t join fetch t.members`
- 패치조인도 조인된 뷰를 통해서 데이터를 조회하는 것인데, 한방쿼리로 select절에 모든 데이터를 모두 조회하는것이다.
- 일대다 연관관계를 조인한 테이블은 데이터가 더많을수있다. 중복된 데이터가 존재할수있다.(일이 2개이상의 레코드와 연관이있으면 중복된 레코드를 가진 뷰가 생성될수있다.)
- 그러므로 distinct를 이용해서 엔티티중복을 제거해야한다(jpq에서는 distinct는 sql에서의 distinct와 같지만 추가로 엔티티중복도 막아준다)

# 명시적 일반조인 vs 패치조인

`select t from Team t join t.members`

- 명시적 일반조인을 이용하면 조인만하지 select절을 통해서 필요한 엔티티를 모두 조회하는건아니다.

- 패치조인을 이용하면 조인은 똑같이 하지만 select절에서 필요한 모든 데이터를 조회한다.
- jpql은 결과를 반환할때 연관관계 고려하지않고 단순히 select절에서 지정한 엔티티만 조회할뿐이다.
- 여기서는 select절에 Member관련 데이터는 없고 Team관련 데이터만존재하므로 Team엔티티만 조회된다.

`select t from Team join fetch t.members`

- 패치조인을 이용하면 select절에 Team관련 데이터뿐만 아니라 Member관련 데이터도 조회되어 한방쿼리가 나가게된다. (즉시로딩개념)
