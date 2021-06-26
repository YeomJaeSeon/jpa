# jpql에서 서브쿼리사용

- SQL에서 서브쿼리사용하는것과 거의 비슷하다.
- 다중레코드를 반환하는 서브쿼리에 대해서는 ALL, ANY를 사용했지만 ANY대신 SOME을 사용해도된다.
- EXISTS 함수를 통해서 서브쿼리에 결과가 존재하는지 확인할수있다.
- IN 함수는 서브쿼리 결과중 하나라도 같은게있으면 참을 반환한다.

# 적용

1. EXISTS사용

- 회원 세명, 팀은 맨유랑 첼시 두개
- 박지성은 맨유, 하베르츠랑 램파드는 첼시팀

- 팀이 아스날인 선수 쿼리하기
- 서브쿼리결과가 아무것도없으니 거짓임.
  `select m from Member m where EXISTS(select t from Team t where t.name='아스날')`

2. ALL 함수 사용

- 위 상황과 같음
- 나이 제일 많은 선수 쿼리하기
- where절에서 서브쿼리이용
- ALL은 모두만족해야한다.
- 모두만족하는 녀석은 나이가 제일많은 박지성 22살밖에없으므로 해당 Member가 결과로 조회된다.
  `select m from Member m where m.age > ALL(select m2.age from Member m2 where m.username <> m2.username)`

3. ANY, SOME
   `select m from Member m where m.age > ANY(select m2.age from Member m2 where m.username <> m2.username)`

# jpa에서 서브쿼리

- where, having절에서밖에 사용못함
- hibernate를 사용하면 select절에서 서브쿼리사용가능
- from절에선 서브쿼리 아애 사용불가
