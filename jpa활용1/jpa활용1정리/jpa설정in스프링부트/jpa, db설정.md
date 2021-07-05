# yml

- DB 드라이버 클래스와, url에대한 설정을 `datasource`에 설정
- jpa에 대한설정도함. ddl-auto : create와 같은 설정
- sql을 출력할것인가 말것인가에 대한설정도 yml에서함
  (yml은 2개의 스페이스바로 구분!)

- 로그레벨도 설정할수있음(debug에서할지 INFO에서할지..)

> yml에서 DB 드라이버 클래스, url, jpa(hibernate, ddl option, sql formatting등을 설정함. 간단하게), 로그관련도 이곳에서 설정

# jpa test

- `Repository`에서 db에 접근하는 test를 해보자.
- entityManger을 통해서 영속성컨텍스트에 접근했었다.
- 전에는 순수 spring을 사용해서 em을사용했기에 persistence를 통해서 entitymangerFactory를 사용했음
- 스프링 부트와 yml로 이러한 설정이 자동화되어있으므로 스프링부트(data jpa)를 사용하면 `@PersistenceContext`라는 에너테이션과 함께 entitymanager를 불러올수있음!(간단하다.)

- Repository example

```java
@Repository
public class MemberRepository {

    // entitiymangerfactortoy 이런거 스프링부트가 자동으로 생성 우리는 그냥쓰면된다.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}

```

- spring boot, yml로 너무간단하게 jpa를 스프링에서 사용할수있땅..

- 참고로 jpa에서 데이터변경은 **한 트랜잭션 내에서** 해야한다!! 무조건!
- 그러므로 `@Transactional` 에너테이션으로 트랜잭션을 제공한다.

- 데이터 변경 in jpa example

```java
//junit 5

    @Test
    @Transactional
    @Rollback(value = false)
    public void test(){
        Member member = new Member();
        member.setUsername("memberA");
        memberRepository.save(member);

        Member findMember = memberRepository.find(member.getId());

        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
        assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member : " + (findMember == member));
    }
```

- test코드내에서 `@Transactional`을 사용하면 자동으로 롤백이일어난다. 테스트이므로 DB에 데이터 반영되면 안되늰까!
- 참고로 한 트랜잭션내에서 jpa는 엔티티의 동일성을 보장한다. 이는 영속성 컨텍스트의 1차캐싱때문이다.! 위 코드는 select 쿼리가 안나간다. 영속성컨텍스트에 1차 캐싱 되어있기 때문에.

# sql 쿼리 파라미터 ?? 해결

- 외부라이브러리 `implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'`를 사용하면 ?, ? 쿼리파라미터에 어떠한 값이 들어가는지 알수있따!
- System.out 으로 출력된다. 로깅이 아니므로 실제 운영서버세어 사용하려면 성능 테스트가 필요함.

> 스프링 부트에서 jpa를 사용하는건 너무 편하다. @PersistenceContext 애너테이션으로 엔티티매니져 바로 얻어올수있고 이를 통해 영속성 컨텍스트에 쉽게접근가능! spring boot사용안했을땐 엔티티매니져 팩토리에서 직접 엔티티 매니져 얻어와서 영속성컨텍스트에 접근해야했음, 그리고 @Transactional같은 애너테이션으로 쉽게 작업이 가능하네!! 그리고 설정도 편하다. 스프링부트가 자동으로 설정해주는것이 많기때문에!(단순히 spring data jpa 의존성 라이브러리만 설치했을뿐인데..)
