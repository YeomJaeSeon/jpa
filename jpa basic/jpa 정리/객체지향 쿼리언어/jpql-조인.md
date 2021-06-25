# 조인

1. 내부조인 (JOIN)
2. 외부조인 (LEFT JOIN)
3. 세타조인 (~ From tableA, tableB where tableA.name = tableB.username)

- 이 조인들에 대해서 jpql에서는 어떻게 사용할까

# jpql에서 조인

1. 내부조인

`select m from Member m join Team t`;

- SQL에서는 ON으로 조인할 PK, FK를 지정해줘야하는데 jpql에서는 그럴필요없음. 이미 연관관계있는거 jpa가 알기때문에, 그래서 저렇게 연관관계있는 테이블을 지정해주기만하면됨. 물론 alias는 꼭붙여야함

2. 외부조인

`select m from Member m left join Team t`

- left만 지정하면된다

3. 세타조인

`select m from Member m, Team t where m.username=t.name`

- 너무간단하다

# jpql에서 on절

- 하이버네이트 5.1버전부터 등장함
- jpql에서의 on절을 이용하면 두가지 기능을 이용할수있다.

1. 조인 대상 필터링

`select m from Member m join Team t on t.name='A'`

- 이렇게 조인할 대상을 필터링을 해버릴수있따
- 실제쿼리는 ON절에 PK = FK와 AND로 추가된 필터링 구문이 포함되어있다.

2. 연관관계없는 녀석끼리 조인가능

- jpql에선 연관관계가 없으면 조인이 불가능해야함 원래는. 연결할 pk, fk가 없기떄문에
- jpql에서 on절을 이용하면 연관관계없는 엔티티끼리도 조인이 가능하게할수있음, 조인할 멤버변수를 지정하면됨
- Member엔티티와 Product엔티티 아무 관계없음 그래도 조인하고싶어
- Member엔티티의 username필드와 Product엔티티의 name필드를 통해서 조인하고싶어 . 이때 on절을 이용
  `select m from Member m join Product p on m.username=p.name`
- 여기서잠깐! 이 jpql 쿼리에서 프로젝션되는 타입은 뭘까?(이부분이 헷갈렷음 순간..)
- 바로바로 **Member**이다
- 아무리 조인되더라도 프로젝션 되는 엔티티는 m으로 지정되었기에 타입을 Member로 지정할수있따. 그래서 반환되는값도 TypedQuery이다
