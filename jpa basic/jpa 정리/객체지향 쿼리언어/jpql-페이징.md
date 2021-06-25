# 페이징

- MySQL RDBMS에서 페이징은 limit과 offset을 사용했다
- offset 인덱스부터 limit 개를 가져올거다 라는 의미이다.
- 대표적인 예로 게시판에서 가져올 게시글

- jpql을 사용하면 페이징을 엄청간단하게 할수있따.

# jpql 페이징

- `setFirstResult()`로 어디서부터, `setMaxResults()`로 몇개를 가져올지 지정할수있따.
- jpql을 통해 위 api로 페이징을 하면 방언에 맞게 쿼리가 변환되어서 나가게 된다.

> 간단했다.
