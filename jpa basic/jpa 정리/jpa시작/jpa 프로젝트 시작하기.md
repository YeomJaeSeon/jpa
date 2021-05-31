# jpa 프로젝트 시작하기

- Maven을 이용할것이다. (gradle말고..)
- Maven을 이용하므로 pom.xml에서 의존 모듈들을 설정하면됨.

`pom.xml`

```
    <dependencies>
        <!-- JPA 하이버네이트 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.3.10.Final</version>
        </dependency>
        <!-- H2 데이터베이스 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.200</version>
        </dependency>

        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.3.0</version>
        </dependency>
    </dependencies>
```

- jpa가 사용할 구현체 hibernate와 h2데이터베이스 모듈을 다운받는다. javax.xml.bind는 java11에서 사용할때 필요한 모듈

- jpa설정할 `persistence.xml`파일을 src/main/resources/META-INF/폴더 아래 만든다. 그리고 자바 표준 속성에대한 설정과 하이버네이트 전용 속성에 대한 설정을 진행한다.

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
</persistence>
```

- hibernate전용속성에서 dialect는 DB방언을 알아먹을수 있도록 변환해줌. mysql이나 oracle에대한 dialect도 존재함. h2DB를 사용하므로 h2 dialect를 이용
- 또 show_sql, format.. 등으로 하이버네이트 옵션을 추가할수있고
- 내가 처음에 헤맸던 부분인 auto create hibernate속성을 추가하면 최초에 알아서 테이블을 생성해주는데 처음에 테이블 생성한다음 또 저 속성을 적용하면 계속 테이블이 생성되서 제대로 데이터가 테이블에 들어가지않을수있다. 그러므로 최초에 저 속성 살리고 한번 테이블 만든뒤에는 주석달자.. (아니면 이 속성 사용안하고 직접 테이블만들면됨. - DB에 직접.)

- 그리고 entityManagerFactory만들때 필요한 jpa unit 이름을 persistence.xml에서 지정한다. `hello`로 지정함. `<persistence-unit name="hello">`!!

# jpa구동방식

- Persistence클래스가 `META-INF/persistence.xml`의 jpa설정정보를 보고 EntityManagerFactory를 생성한다.
- EntityManagerFactory로부터 EntityManager가 생성된다.
- EntityManagerFactory는 was뜰때부터 내려갈떄까지, EntityManager는 하나의 트랜잭션 동안..의 생명주기를 갖는다.

# em, emf사용하기

```java
@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```

- `@Entity`라는 애너테이션을 클래스레벨에 붙이면 jpa를 사용하는 클래스다라고 jpa에게 알려준다.
- `@Id`는 PK를 지정해준다.

```java
public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // entity manager factory생성 - 여기서 entitymanager를 생성함
        // emf로 줄여서말하겠따. emf는 was뜰때 열고 그리고 was 내려갈때 닫으면됨.
        // 매개변수로 persistence.xml에서설정한 unitname을 넣으면된다.

        EntityManager em = emf.createEntityManager();
        //emf로 부터 em생성 - 하나의 트랜잭션에서 사용됨 트랜잭션 종료되면 em도닫자!
        // jpa에서의 데이터변경은 모두 트랜잭션 내부에서사용하자. db도 내부적으로 트랜잭션 다 사용함.

        EntityTransaction tx = em.getTransaction();
        // em으로부터 트랜잭션 얻기
        tx.begin();// 트랜잭션 시작

        try{
            Member member = new Member();
            member.setId(3L);
            member.setName("userC");

            em.persist(member); // INSERT

            tx.commit(); // 트랜잭션 커밋 - DB에 적용
        }catch (Exception e){
            tx.rollback(); // 롤백
        }finally {
            em.close(); // 하나의 트랜잭션 끝나면 em 닫기
        }

        emf.close();
    }
```

# JPA사용 주의점

1. EntityManagerFactory는 어플내에서 하나만생성해서 전체에서 공유하낟.
2. EntityManager는 쓰레드간에 공유하지않는다.(사용하고 버리자. )
3. JPA의 모든 데이터변경은 트랜잭션 내에서 실행한다.(그래서 em으로부터 트랜잭션 생성해서 데이터 추가하고 다하면 트랜잭션 commit한다.)

# jpql사용하기

- 간단한 select, insert 가아니면? jpql이용한다.
- 특징으로는 객체 엔티티에 적용되는 sql이다.
- 원래 sql은 테이블에 적용되야하는데 jpql은 객체 엔티티에 적용된다.
- 객체지향 sql이다!

`List<Member> resultList = em.createQuery("select m from Member as m", Member.class).getResultList();`

- 중요한건 sql대상이 객체이다. 테이블이 아니다!!
