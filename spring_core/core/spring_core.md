# 스프링 핵심 원리

### 목차
### [1. 객체 지향 설계와 스프링](#1-객체-지향-설계와-스프링-1)
### [2. 객체 지향 원리 적용](#2-객체-지향-원리-적용-1)
### [3. 스프링 컨테이너와 스프링 빈](#3-스프링-컨테이너와-스프링-빈-1)
### [4. 싱글톤 컨테이너](#4-싱글톤-컨테이너-1)
### [5. 컴포넌트 스캔](#5-컴포넌트-스캔-1)
### [6. 의존관계 자동 주입](#6-의존관계-자동-주입-1)
### [7. 빈 생명주기 콜백](#7-빈-생명주기-콜백-1)
### [8. 빈 스코프](#8-빈-스코프-1)

# 1. 객체 지향 설계와 스프링

## 1.1 스프링이란?

> 자바 언어 기반의 `프레임워크`  
참고 - [API & 라이브러리 & 프레임워크](https://www.notion.so/API-983e3cde711849a7bc5842d6f0628868)
>
- 스프링은 자바 언어의 특징인 `객체 지향`을 가장 잘 살려서 애플리케이션을 개발할 수 있도록 도와주는 프레임워크임
- 스프링 의미
    - 스프링 프레임워크
        - 핵심 기술 : 스프링 DI 컨테이너, AOP, 이벤트 등
    - 스프링 DI 컨테이너 기술
    - 스프링 생태계

      → 문맥에 따라 다르게 해석함

- 스프링 부트 → 스프링을 편리하게 사용할 수 있도록 지원하는 것
    - 단독으로 실행할 수 있는 스프링 애플리케이션을 쉽게 생성하도록 도와줌
    - Tomcat 같은 웹 서버를 내장
    - 외부 라이이브러리를 자동으로 구성해줌

## 1.2 객체 지향

- 객체 지향 특징 4가지
    1. 추상화
    2. 캡슐화
    3. 상속
    4. `다형성`
- 객체 지향 프로그래밍
    - 프로그램을 명령어의 모음이 아닌 독립된 단위, 즉 객체들의 모임으로 보는 것
    - 객체는 메시지를 주고 받아 데이터를 처리할 수 있음 (객체 간 협력)
    - 프로그램을 `유연`하고 `변경이 용이`하게 만들기 때문에 대규모 소프트웨어 개발에 많이 사용됨
- 다형성
    - 세상을 `역할`과 `구현`으로 분리하여 프로그램을 유연하고 변경이 용이하게 만들어 줌
    - 예시

        | 역할 | 구현(체) |
        | --- | --- |
        | 자동차 | k3, 아반떼, 제네시스 ... |
        | 로미오 역 | 장동건, 원빈 ... |
        | 줄리엣 역 | 김태희, 송혜교 ... |

        - 어떤 자동차를 타든 운전 면허만 있으면 운전을 할 수 있음
            - 엑셀을 밟으면 앞으로 감
            - 브레이크를 밟으면 멈춤
        - 줄리엣 역할을 누가 맡던 로미오 역할을 바꿀 수 있음
    - 역할 → 인터페이스
    - 구현 → 인터페이스를 구현한 클래스, 구현 객체
        - 인터페이스를 구현한 구현 객체를 실행 시점에 유연하게 변경할 수 있음
            
            → 클라이언트를 변경하지 않고 서버의 구현 기능을 유연하게 변경할 수 있음
            
    - 제어의 역전(IoC), 의존관계 주입(DI)는 다형성을 활용해 역할과 구현을 편리하게 다룰수 있도록 지원함

## 1.3 객체 지향 설계의 5가지 원칙 (SOLID)

1. SRP (Single Responsibility Principle) - 단일 책임 원칙
    - 하나의 클래스는 하나의 책임만 가져야 함
    - 클래스의 변경이 있을 때 파급 효과가 적으면 단일 책임 원칙을 잘 따른 것
2. `OCP` (Open/Closed Principle) - 개방/폐쇄 원칙
    - 소프트웨어 요소가 `확장에는 열려`있으나 `변경에는 닫혀`있어야 함
        - 다형성을 활용
            - 인터페이스를 구현하는 새로운 구현 클래스를 만들 수 있음 → 확장에는 열려있음
            - 새로 구현 클래스를 사용한다고 기존 코드(클라이언트)를 변경할 필요는 없음 → 변경에는 닫혀있음
    - 자바 코드만으로는 기존 코드(클라이언트)의 변경 없이 인터페이스 구현체를 변경할 수 없음

      → OCP 위반

      → 객체를 생성하고 연관 관계를 맺어주는 별도의 설정자가 필요함

      = 스프링 컨테이너

3. LSP (Liskov Substitution Principle) - 리스코프 치환 원칙
    - 다형성을 지원하기 위한 원칙
    - 인터페이스 구현체를 믿고 사용하려면 구현체(하위 클래스)는 인터페이스 규약을 다 지켜야 함

      ex) 자동차 인터페이스 - 엑셀을 밟았는데 뒤로감 → LSP 위반

4. ISP (Interface Segregation Principle) - 인터페이스 분리 원칙
    - 범용 인터페이스 하나보다 인터페이스 여러 개가 나음
        - 자동차 인터페이스 → 운전 인터페이스 + 정비 인터페이스
    - 인터페이스가 명확해지고 대체 가능성이 높아짐
5. `DIP` (Dependency Inversion Principle) - 의존관계 역전 원칙
    - 프로그래머는 추상화에 의존해야지 구체화에 의존하면 안됨
        - 구현체에 의존하지 말고 인터페이스에 의존해야 함
    - 자바 코드만 사용할 경우 인터페이스와 구현체에 동시에 의존함

      → DIP 위반

- 결론
    - 다형성만으로는 OCP, DIP를 지킬 수 없다.
    - 뭔가가 더 필요하다. → 스프링 컨테이너!

## 1.4 다시 스프링으로

- 좋은 객체 지향 프로그램
    - OCP, DIP 원칙을 지켜야 하는데 다형성만으로는 지킬 수 없음
    - OCP, DIP를 지키기 위해 DI 컨테이너가 필요하고 이것을 프로그래머가 직접 만들기엔 할 일이 너무 많아짐
    - 그래서 만들어진 것이 스프링 프레임워크
- 스프링
    - DI 컨테이너를 제공해 다형성 + OCP, DIP를 가능하게 지원함

      → 클라이언트의 코드 변경 없이 서버 기능을 확장할 수 있음

      → 부품을 교체하듯이 쉽게 개발할 수 있음
      
# 2. 객체 지향 원리 적용
# 2. 객체 지향 원리 적용

## 2.1 예제 기존 코드

```java
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
}
```

```java
public class OrderServiceImpl implements OrderService {
  //  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
      private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
}
```

- 인터페이스에 의존함과 동시에 구현 클래스(구현체)에도 의존함
    
    → DIP 위반
    
- 기능을 확장하기 위해 구현체를 바꾸려면 기존 코드를 변경해야 함 = 클라이언트 코드(ServiceImpl)를 변경해야 함
    
    → OCP 위반
    
- 관심사 분리
    - 인터페이스와 구현체를 동시에 의존하는 것은 로미오 역할의 장동건이 줄리엣 역할로 김태희를 직접 초빙하는 것과 같음
    - 배우는 본인의 역할인 배역에만 집중해야 함
    - 별도의 공연 기획자가 존재해야 함

<aside>
❗ 클라이언트 코드가 인터페이스에만 의존하도록 변경해야 함!

</aside>

## 2.2 인터페이스에만 의존하도록 변경한 코드

```java
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
}
```

```java
public class OrderServiceImpl implements OrderService {
      private final DiscountPolicy discountPolicy;
}
```

- DIP 원칙을 따르는 상태
- 이렇게 되면 구현체가 없으므로 실행 시 NPE 예외가 발생함
- 구현체를 주입해줄 누군가가 필요함

## 2.3 AppConfig

- 구현 객체를 생성하고 연결하는 책임을 가지는 설정 클래스

```java
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
		
		// MemberRepository 인터페이스의 구현체로 MemoryMemberRepository 사용됨
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

		// DiscountPolicy 인터페이스의 구현체로 RateDiscountPolicy 사용됨
    private DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
```

- MemberServiceImpl, OrderServiceImpl은 생성자를 통해 외부에서 구현체(인스턴스 참조)를 주입받음
    
    → 의존관계 주입 (DI)
    
- 만약 DiscountPolicy 인터페이스 구현체를 바꾸고 싶다면?
    - discountPolicy() 메소드만 수정해주면 됨 → 클라이언트 코드는 변경하지 않음
    - OCP 원칙을 따르는 상태

## 2.4 IoC & DI & 컨테이너

### IoC (Inversion of Control) - 제어의 역전

> `프로그램의 흐름 제어`를 구현 객체가 직접 제어하는게 아니라 `외부에서 제어`하는 것
> 
- 기존 프로그램은 구현 객체가 프로그램의 제어 흐름을 스스로 조종했음
- AppConfig 등장 이후
    - 구현 객체는 자신의 로직을 실행하는 역할만 담당
        - OrderServiceImpl은 인터페이스를 호출하지만 어떤 구현 객체가 들어올지 모름
    - 프로그램의 흐름 제어는 AppConfig가 담당
        - MemberServiceImpl, OrderServiceImpl도 AppConfig가 생성함
        - MemberService 인터페이스의 다른 구현체를 사용할 수 도 있음
- 프레임워크 vs 라이브러리
    - 프레임워크
        - 프레임워크가 내가 작성한 코드를 제어하고 대신 실행해줌 (JUnit)
    - 라이브러리
        - 내가 작성한 코드가 제어의 흐름을 담당함

### DI(Dependency Injection) - 의존 관계 주입

> `실행 시점(런타임)`에 `외부에서 구현 객체를 생성`하고 `클라이언트에 전달`해서 클라이언트와 서버의 실제 의존 관계가 연결되는 것
> 
- 의존 관계
    - 클래스 의존 관계
        - 정적 → 애플리케이션 실행 전 결정되는 의존 관계
        - import 코드만 보고 의존 관계를 파악할 수 있음
        - 인터페이스에 어떤 구현체가 들어올지는 알 수 없음
    - 객체 의존 관계
        - 동적 → 애플리케이션 실행 시점에 결정되는 의존 관계
        - 외부에서 객체 인스턴스를 생성하고 그 참조값을 전달하여 연결됨
        - DI는 객체 의존 관계 주입을 의미하는 것
- DI를 사용하는 이유
    - 정적인 클래스 의존 관계를 변경하지 않고 동적인 `객체 의존 관계를 쉽게 변경`할 수 있음

### IoC 컨테이너, DI 컨테이너

> `객체를 생성하고 관리`하면서 `의존 관계를 연결`해 주는 것
> 
- 주로 DI 컨테이너라고 함
- AppConfig가 DI 컨테이너의 역할을 함
    - 이 후 배울 spring이 DI 컨테이너 역할을 함
- 어셈블러, 오브젝트 팩토리 등으로 불리기도 함

## 2.5 AppConfig를 spring 기반으로 변경

```java
@Configuration      // 스프링 컨테이너가 설정 정보로 사용하는 클래스
public class AppConfig {

    // 메서드 명 : key -> 스프링 빈 이름
    // 리턴 객체 : value

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
```

```java
class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    void setup() {
        // spring 사용 전
        // AppConfig appConfig = new AppConfig();
        // memberService = appConfig.memberService();

        // spring 사용 후
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        memberService = ac.getBean("memberService", MemberService.class);
    }

    @Test
    void 회원가입_회원조회() {
        //given
        Member member = new Member(1L, "A", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        assertThat(member).isEqualTo(findMember);
    }
}
```

- ApplicationContext : 스프링 컨테이너
- @Configuration : 스프링 컨테이너가 설정 정보로 사용할 클래스에 붙이는 어노테이션
- @Bean : 스프링 컨테이너에 등록할 스프링 빈이 되는 메소드에 붙이는 어노테이션
    - 메소드 이름 : key → 스프링 빈 이름
    - 리턴 객체 : value
- getBean() 메소드를 사용해 스프링 컨테이너에서 스프링 빈을 찾아서 사용해야 함

<aside>
❓ 그냥 자바 코드로 DI를 하는게 더 간단해 보이는데 왜 스프링 컨테이너를 사용하는가?

</aside>

# 3. 스프링 컨테이너와 스프링 빈

## 3.1 스프링 컨테이너 생성 과정

1. 스프링 컨테이너 생성
2. 스프링 빈 등록
3. 스프링 빈 의존관계 설정

## 3.2 스프링 빈 조회

- 스프링 컨테이너에는 사용자가 만든 애플리케이션 빈 뿐만 아니라 스프링 내부에서 사용하는 빈들도 존재함
    - beanDefinition.getRole() == BeanDefinition.*ROLE_APPLICATION*
        - 사용자가 만든 빈
    - beanDefinition.getRole() == BeanDefinition.*ROLE_INFRASTRUCTURE*
        - 스프링 내부에서 사용하는 빈
- 스프링 컨테이너에 같은 타입의 빈이 2개 이상인 경우
    - 해당 타입으로만 조회하면 예외가 발생함 (NoUniqueBeanDefinitionException)
    - 빈 이름, 타입을 모두 사용해서 조회하면 정상적으로 조회할 수 있음
- 스프링 컨테이너에 부모가 같은 타입이 2개 이상인 경우
    - 스프링은 부모 타입으로 조회하면 자식 타입도 함께 조회하기 때문에 부모 타입으로 조회하면 예외가 발생함 (NoUniqueBeanDefinitionException)
    - 빈 이름 + 부모 타입을 사용해서 조회하면 정상적으로 조회할 수 있음
    - 자식 타입으로 조회하면 정상적으로 조회할 수 있음 → 권장하진 않음

## 3.3 BeanFactory & ApplicationContext

- BeanFactory
    - 스프링 컨테이너의 최상위 인터페이스
    - 스프링 `빈을 관리`하고 `조회`하는 역할
    - getBean() 제공
    - 직접 사용할 일은 거의 없음
- ApplicationContext
    - BeanFactory를 상속받은 인터페이스
    - 빈을 관리하고 조회하는 기능 외 `다양한 부가기능`을 제공함
        
        ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/a47ee424-a382-41d6-bb8b-39cf3f29257b/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220315%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220315T170250Z&X-Amz-Expires=86400&X-Amz-Signature=de61c86a7fb6db674dd979d41d6f0a397baf1fa69a2d82737e81c98779d2fa2e&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
        

## 3.4 스프링 컨테이너 설정

- 스프링 빈은 `다양한 형식의 설정 정보`를 이용해 만들 수 있음
    - ex) java, XML, Groovy 등
    - AppConfig 클래스를 이용해 설정 정보를 등록한게 java를 이용한 것
- 스프링 컨테이너는 `BeanDefinition`을  기반으로 스프링 빈을 생성함
    - BeanDefinition
        - 빈 설정 메타 정보
        - @Bean 당 하나씩 메타 정보가 생성됨
- 어떤 형식인지 상관 없이 스프링 컨테이너는 BeanDefinition만 알면 된다!
    - BeanDefinition 추상화
        
        → 역할 & 구현 분리
        
        ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/e7a1aeab-c9cb-4453-bb68-fd01d86c9356/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220315%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220315T170313Z&X-Amz-Expires=86400&X-Amz-Signature=1fd8963693c2ae35793a24c2d0613555676f1c85a05becd275bcbb01c20f9e39&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

# 4. 싱글톤 컨테이너

## 4.1 웹 애플리케이션과 싱글톤 패턴

- 웹  애플리케이션 → 여러 고객이 동시에 요청함
- 스프링 없는 순수 DI 컨테이너
    - 고객이 요청할 때 마다 객체를 새로 생성함 → 메모리 낭비가 심함
- 싱글톤 패턴
    
    > 클래스의 인스턴스가 딱 1개만 생성되도록 보장하는 디자인 패턴
    > 
    - static 영역에 미리 1개의 객체만 생성해두고 getter를 통해서만 조회할 수 있음
        - 싱글톤을 구현하는 방법은 다양함. 미리 생성하는게 가장 간편한 방법
    - private 생성자를 통해 외부에서 new 키워드를 사용해 객체를 생성하지 못하도록 막음
    
    ```java
    public class SingletonService {
    
        private static final SingletonService instance = new SingletonService();
    
        // 외부에서 new 를 사용해 객체를 생성하지 못함
        private SingletonService() {
        }
    
        public static SingletonService getInstance() {
            return instance;
        }
    }
    ```
    
    - 여러 고객의 요청이 있을 때 이미 만들어진 하나의 객체를 공유해서 사용하므로 휴율적으로 사용할 수 있음
- 싱글톤 패턴의 문제점
    - 구현 코드 자체가 많음
    - 클라이언트가 구체 클래스에 의존함 (구체 클래스.getInstance())
        - DIP, OCP 위반
    - 테스트하기 어려움 → 유연성 떨어짐

## 4.2 싱글톤 컨테이너

> 싱글톤 패턴의 문제를 모두 해결해면서 객체를 싱글톤으로 관리할 수 있게 해주는 역할
> 
- 스프링 컨테이너
    - 싱글톤 컨테이너 역할을 함
    - 스프링 빈 → 싱글톤으로 관리되는 빈
    - 싱글톤 레지스트리
        - 싱글톤 객체를 생성하고 관리하는 기능
    
    cf) 스프링 빈은 싱글톤으로만 사용할 수 있는 것은 아님. 뒤에 스코프에서 자세히
    

## 4.3 싱글톤 방식 주의점

- 무상태(stateless)로 설계해야 함
    - 싱글톤 방식은 여러 클라이언트가 하나의 객체를 공유해서 사용함
    - 특정 클라이언트가 객체의 값을 변경할 수 있는 필드가 있으면 안됨
    - 공유되는 필드 대신 지역 변수, 파라미터 등으로 사용하는게 좋음
    
    <aside>
    ❗ 스프링 빈 필드에 공유 되는 필드가 변경될 수 있으면 큰 장애가 발생할 수 있음
    
    </aside>
    
    ```java
    package spring.corepractice.singleton;
    
    public class StatefulService {
    
        // 공유되는 필드
        private int price;
    
        // 이 메소드를 통해 공유되는 필드가 변경될 수 있음 -> 장애가 발생할 수 있음
        public void order(String name, int price) {
            System.out.println("name = " + name + ", price = " + price);
            this.price = price;
        }
    
        public int getPrice() {
            return price;
        }
    }
    ```
    

## 4.4 @Configuration과 바이트코드 조작

```java
@Configuration      
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
```

- 스프링 컨테이너가 각 @Bean을 호출해서 스프링 빈을 생성함
    - 이 때 memberRepository()는 3번 호출됨
    - 자바 코드 상으로 MemoryMemberRepository 객체는 3개가 생성되어야 함
        - 테스트 코드로 확인해보면 memberRepository() 메소드는 1번만 호출되고 MemoryMemberRepository 객체는 1개만 생성됨
            
            → 싱글톤으로 관리되고 있음
            
- @Configuration
    
    > 바이트코드를 조작하는 라이브러리(CGLIB)를 이용해 스프링 빈이 싱글톤이 되도록 보장해줌
    > 
    - AnnotationConfigApplicationContext에 파라미터로 넘긴 값도 스프링 빈으로 등록됨
    - AppConfig 스프링 빈을 조회해 클래스 정보를 확인해보면 순수 클래스가 아님을 확인할 수 있음
    
    ```java
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    
    System.out.println("AppConfig : " + ac.getBean(AppConfig.class));
    // AppConfig : spring.corepractice.AppConfig$$EnhancerBySpringCGLIB$$a241884e@8c11eee
    ```
    
    - 스프링이 CGLIB 라이브러리를 사용해 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들어 스프링 빈으로 등록함
    - `AppConfig@CGLIB` → AppConfig
        - AppConfig가 부모 타입이므로 AppConfig로 조회할 수 있음
    - 스프링이 만든 `임의의 클래스`가 스프링 빈을 싱글톤이 되도록 보장해줌
        
        ```java
        @Override
        @Bean
        public MemberRepository memberRepository() {
        	if(1.스프링 빈이 스프링 컨테이너에 등록되어 있으면?) {
        		return 스프링 컨테이너에서 찾아서 반환;
        	} else { //2.스프링 빈이 스프링 컨테이너에 등록되어있지 않으면?
       			기존로직 호출 -> 스프링 빈을 생성해 스프링 컨테이너에 등록				
       			return 스프링 컨테이너에서 찾아서 반환;
       		}
        }
        ```
        
        - 결과적으로 스프링 컨테이너에 스프링 빈이 있으면 찾아서 반환하고, 없으면 스프링 빈을 생성해 등록하고 반환하므로 1개의 스프링 빈만 만들어짐 → 싱글톤
- @Configuration 없이 @Bean만으로 설정 정보를 구성한다면?
    - 스프링 컨테이너는 만들 수는 있지만 싱글톤을 보장하지 않음 (CGLIB 라이브러리를 사용하지 않기 때문)
- 항상 @Configuration을 사용하자!

# 5. 컴포넌트 스캔

## 5.1 ComponentScan & Autowired

- 스프링 빈이 수십, 수백개가 되는 경우 빈을 일일이 등록하기 힘들고 설정 정보도 커짐
- @ComponentScan & @Component
    - 설정 정보 없이 스프링 빈을 등록하는 기능을 제공
    - 빈으로 등록할 클래스 → @Component
        - 스프링 빈 이름 : 클래스명 맨 앞글자만 소문자로 사용
    - 설정 정보 클래스 → @ComponentScan
        - @Component 붙은 클래스 스캔해서 스프링 빈으로 등록
- @Autowired
    - 스프링 컨테이너에서 자동으로 해당 스프링 빈을 찾아서 주입해줌
    - getBean(MemberRepository.class)와 유사

```java
@Configuration
@ComponentScan
public class AutoAppConfig {
}
```

```java
@Component
public class MemberServiceImpl implements MemberService{
		
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

## 5.2 탐색 위치

- 컴포넌트 스캔을 시작할 시작 위치를 지정할 수 있음
    - basePackages : 해당 패키지부터 하위 모든 패키지 탐색
    - basePackageClasses : 해당 클래스가 위치한 패키지를 시작 패키지로 지정
    - 지정하지 않으면 해당 설정 정보 클래스가 있는 패키지를 시작 패키지로 지정
    
    ```java
    @ComponentScan(
    	basePackages = "hello.core",
    	basePackageClasses = Start.class
    )
    ```
    
- 일반적으로 basePackages를 사용하지 않고 설정 정보 클래스를 프로젝트의 최상단에 둠

## 5.3 스캔 대상

- 컴포넌트 스캔 기본 대상
    1. @Component
    2. @Controller
        - 스프링 MVC 컨트롤러
    3. @Service
        - 스프링 비지니스 로직
    4. @Repository
        - 스프링 데이터 접근 계층
            - 부가 기능 : 데이터 계층 예외를 스프링 예외로 변환해줌
    5. @Configuration
        - 스프링 설정 정보
            - 부가 기능 : 스프링 빈이 싱글톤을 유지하도록 처리

## 5.4 필터

- includeFilters → 거의 사용x
    - 컴포넌트 스캔 대상을 추가로 지정
- excludeFilters
    - 컴포넌트 스캔에서 제외할 대상을 지정
        
        ```java
        @ComponentScan(
             // Configuration 어노테이션이 붙은 클래스를 컴포넌트 스캔에서 제외함
             // cf) @Configuration 에 @Component 포함되어 있음
             excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        )
        ```
        
- FilterType 옵션 존재 → 잘 안씀

## 5.5 중복 등록과 충돌

- 수동 빈 등록(@Bean) vs 자동 빈 등록(@Component)
    - 동일한 스프링 빈 이름을 가진 경우 수동 빈 등록(@Bean)이 우선임 (오버라이딩 해버림)
    - 테스트 코드에서는 이상없이 실행되지만 스프링 부트(CoreApplication) 실행 시 에러가 발생함
        - 애매한 상황을 만들지 않기 위해
        - property에 `spring.main.allow-bean-definition-overriding=true` 추가하면 정상 실행됨

<aside>
❗ 웬만하면 동일한 스프링 빈 이름을 사용하지 말자. 잡기 어려운 버그가 만들어진다.

</aside>

# 6. 의존관계 자동 주입

## 6.1 의존관계 주입 방법

1. `생성자 주입` → 이 방법만 쓰자!
    - 생성자 호출 시점 1번만 호출됨
        - 한번 호출 후 `불변`
        - 생성자 호출 시 `반드시 초기화` 되어야 함 `(final)`
        - 생성자 호출 시 데이터가 `누락`되면 컴파일 오류가 발생함
    - 생성자가 1개만 존재하는 경우 @Autowired 생략할 수 있음
2. 수정자 주입 (setter 주입)
    - 의존 관계를 선택할 수 있음
    - 의존 관계 변경 가능성이 있는 경우 사용 → 거의 없음
3. 필드 주입
    - 변수 선언 부분에 @Autowired 붙임
    - 테스트하기 힘들고 DI 프레임워크 없으면 쓸모가 없음
        - 쓰지 말자
4. 일반 메서드 주입 → setter 주입과 거의 동일

<aside>
❗ 기본으로 생성자 주입을 사용하자. 꼭 필요한 경우 옵션으로 수정자 주입을 사용할 수 있다.

</aside>

## 6.2 옵션

- @Autowired(required = true)
    - default
    - 자동 주입 대상이 없는 경우 → 에러가 발생함
- @Autowired(required = false)
    - 자동 주입 대상이 없는 경우 → 메소드 자체가 실행되지 않음
- @Nullable 사용
    - 자동 주입 대상이 없는 경우 → null 주입
- Optional<> 사용
    - 자동 주입 대상이 없는 경우 → Optional.empty 주입

```java
public class AutowiredOptionTest {

    @Test
    void autowired_option() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
    }

    static class AutowiredConfig {

        //@Autowired(required = true) -> default
        // 자동 주입할 스프링 빈이 없는 경우 -> 에러 발생

	// Member 스프링 컨테이너에 등록 x

        // 자동 주입할 스프링 빈이 없는 경우 -> 메소드 호출 x
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("AutowiredConfig.setNoBean1 : " + member);
        }

        // 자동 주입할 스프링 빈이 없는 경우 -> null 주입
        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("AutowiredConfig.setNoBean2 : " + member);
        }

        // 자동 주입할 스프링 빈이 없는 경우 -> Optional.empty 주입
        @Autowired
        public void setNoBean2(Optional<Member> member) {
            System.out.println("AutowiredConfig.setNoBean3 : " + member);
        }
    }
}
```

> [출력]
> 
> 
> AutowiredConfig.setNoBean3 : Optional.empty
> AutowiredConfig.setNoBean2 : null
> 

## 6.3 롬복 - @RequiredArgsConstructor

- @RequiredArgsConstructor
    - 롬복 라이브러리가 제공하는 기능
    - final이 붙은 필드를 모아 컴파일 시점에 생성자를 자동으로 만들어줌
    
    ```java
    @Component
    @RequiredArgsConstructor
    public class OrderServiceImpl implements OrderService {
    
        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;
    }
    ```
    
    - 코드가 매우 간결해짐

## 6.4 조회 빈이 2개 이상인 경우

- @Autowired
    - 타입으로 조회함
    - getBean(Service.class)와 유사
        
        → 조회하는 타입이 2개 이상인 경우, 자식 타입이 2개 이상인 경우 예외 발생
        
- 예시
    
    ```java
    @Component
    public class OrderServiceImpl implements OrderService {
    
        private final DiscountPolicy discountPolicy;
    
        @Autowired
        public OrderServiceImpl(MemberRepository memberRepository, 
				DiscountPolicy discountPolicy) {
            this.discountPolicy = discountPolicy;
        }
    }
    ```
    
    ```java
    @Component
    public class FixDiscountPolicy implements DiscountPolicy{
    
    }
    ```
    
    ```java
    @Component
    public class RateDiscountPolicy implements DiscountPolicy{
    
    }
    ```
    
    - DiscountPolicy 타입의 스프링 빈이 2개인 경우
        - DiscountPolicy 타입으로 조회하면 NoUniqueBeanDefinitionException이 발생함
- 해결 방법
    1. @Autowired 필드명으로 매치
        - 먼저 타입 매칭을 시도한 뒤, 여러 빈이 있을 경우 파라미터 이름으로 빈을 추가 매칭함
        
        ```java
        @Component
        public class OrderServiceImpl implements OrderService {
        
            private final DiscountPolicy discountPolicy;
        
            @Autowired
            public OrderServiceImpl(MemberRepository memberRepository, 
	    			    DiscountPolicy fixDiscountPolicy) {
                this.discountPolicy = discountPolicy;
            }
        }
        ```
        
    2. @Qualifier 사용
        - Qualifier 추가 구분자를 붙여서 빈을 구분함
            - 빈의 이름을 변경하는 것은 아님
        
        ```java
        @Component
        @Qualifier("mainDiscountPolicy")
        public class FixDiscountPolicy implements DiscountPolicy{
        
        }
        ```
        
        ```java
        @Component
        @Qualifier("subDiscountPolicy")
        public class RateDiscountPolicy implements DiscountPolicy{
        
        }
        ```
        
        ```java
        @Component
        public class OrderServiceImpl implements OrderService {
        
            private final DiscountPolicy discountPolicy;
        
            @Autowired
            public OrderServiceImpl(MemberRepository memberRepository, 
       		@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
                this.discountPolicy = discountPolicy;
            }
        }
        ```
        
        - 만약 mainDiscountPolicy가 붙은 스프링 빈을 찾지 못한다면?
            - mainDiscountPolicy를 이름으로 하는 스프링 빈을 찾음
                
                → Qualifier는 Qualifier를 차즞 용도로만 사용하는게 좋음
                
    3. @Primary 사용
        - 가장 간단한 방법
        
        ```java
        @Component
        @Primary
        public class FixDiscountPolicy implements DiscountPolicy{
        
        }
        ```
        
        ```java
        @Component
        public class RateDiscountPolicy implements DiscountPolicy{
        
        }
        ```
        
        ```java
        @Component
        public class OrderServiceImpl implements OrderService {
        
            private final DiscountPolicy discountPolicy;
        
            @Autowired
            public OrderServiceImpl(MemberRepository memberRepository, 
	    			    DiscountPolicy discountPolicy) {		// fixDiscountPolicy 사용
                this.discountPolicy = discountPolicy;
            }
        }
        ```
        
        - primary가 붙은 스프링 빈을 선택함

## 6.5 어노테이션 직접 만들기

- 조회 빈이 2개 이상인 경우 위의 3가지 방법으로는 컴파일 단계에서 타입 체크가 안 됨
    - 어노테이션을 직접 만들어서 컴파일 시 체크할 수 있도록 하자
1. 어노테이션 만듦

```java
// @Qualifier 에서 가져옴
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
```

2. 동일 타입의 Component에 붙임

```java
@Component
@MainDiscountPolicy
public class FixDiscountPolicy implements DiscountPolicy{
}
```

3. 의존 관계 주입 시 어노테이션을 붙여줌

```java
@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, 
	@MainDiscountPolicy DiscountPolicy discountPolicy) {

        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

## 6.6 Map, List

- 동일한 타입의 빈이 여러 개 인 경우
    - 해당 타입의 빈이 모두 필요할 수도 있음
        
        ex) 클라이언트가 할인의 종류(fix, rate)를 선택할 수 있는 경우
        
    - 해당 타입의 빈을 Map과 List로 받아서 사용할 수 있음

```java
static class DiscountService {

        // 스프링 컨테이너에 등록된 모든 DiscountPolicy 구현체를 Map 과 List 로 만들 수 있음
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);

            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);

            return discountPolicy.discount(member, price);
        }
 }
```

## 6.7 빈 자동 등록 (@ComponentScan, @Component) vs 수동 등록(@Bean)

- 편리한 자동 기능을 기본으로 사용
    - 자동 빈 등록을 사용해도 OCP, DIP를 지킬 수 있음
    - 관리해야 할 빈의 갯수가 많아질 경우 설정 정보를 관리하는 것 자체가 부담스러움 (@Bean으로 관리하기 어려움)
- 수동 등록은 언제 사용하는가?
    - 애플리케이션 → `기술 지원 로직`
        1. `업무 로직`
            - 비지니스 요구사항을 개발할 때 추가, 변경되는 로직 (핵심 비지니스 로직)
            - 숫자가 매우 많고 문제가 발생할 경우 어디서 문제가 발생했는지 파악하기 쉬움
            
            → `자동 빈 등록`을 사용해 편리하게 개발할 수 있음
            
        2. `기술 지원 로직`
            - DB 연결, 공통 로그 처리 등 업무 로직을 지원하기 위한 하부 기술, 공통 기술 로직
            - 업무 로직에 비해 수가 적고 애플리케이션 전반에 걸쳐 광범위하게 영향을 끼침
                - 문제가 발생하면 어디가 문제인지 찾기 힘듦
            
            → `수동 빈 등록`을 사용해 설정 정보에 명확하게 드러내는게 유지보수하기 좋음
            
    - Map, List를 사용하는 경우
        - 위의 예시에서 Map, List에 어떤 스프빙 빈이 들어있는지 코드만 보고 파악하기 어려움
        - 별도의 설정 정보를 만들고 빈을 수동 등록하면 한 눈에 보기 쉬워짐
        
        ```java
        @Configuration
        public class DiscountPolicyConfig {
            
            @Bean
            public DiscountPolicy fixDiscountPolicy() {
                return new FixDiscountPolicy();
            }
        
            @Bean
            public DiscountPolicy rateDiscountPolicy() {
                return new RateDiscountPolicy();
            }
        }
        ```
        
        - 자동 빈 등록으로 사용하고 싶은 경우 클래스를 특정 패키지에 묶어 두는게 좋음
            
            → 핵심은 한 눈에 보고 알 수 있어야 함

# 7. 빈 생명주기 콜백

## 7.1 개요

- 스프링 빈의 **주기에 따라 필요한 작업**이 있음
    - 스프링 `빈의 생성 시`
        - 필요한 연결을 미리 해두는 **초기화 작업 필요**
    - 스프링 `빈의 종료 시`
        - **연결을 모두 종료하는 작업 필요**
    - `문제`
        - `수정자 주입`으로 의존관계를 주입받는 경우
            - 스프링 빈이 생성 시 `의존관계를 주입받기 전에 초기화` 된다면?
                
                ⇒ 스프링 빈이 **제대로 초기화되지 않음**
                
- 예제
    - NetworkClient 클래스
        - url 필드
            - 생성자 주입이 아닌 수정자 주입으로 의존관계를 주입받음
        
        ```java
        public class NetworkClient {
        
            private String url;
        
            public NetworkClient() {
                System.out.println("생성자 호출, url = " + url);
        				connect();
        				call("초기화 연결 메시지");
            }
        
            public void setUrl(String url) {
                this.url = url;
            }
        
            public void connect() {
                System.out.println("connect : " + url);
            }
        
            public void call(String message) {
                System.out.println("call : " + url + " message = " + message);
            }
        
            public void disconnect() {
                System.out.println("close : " + url);
            }
        }
        ```
        
    - 테스트
        
        ```java
        public class BeanLifeCycleTest {
        
            @Test
            public void lifeCycleTest() {
                ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
                ac.getBean(NetworkClient.class);
                ac.close();
            }
        
            @Configuration
            static class LifeCycleConfig {
        
                @Bean
                public NetworkClient networkClient() {
                    // 생성자 호출 -> 생성자 내부에 url 을 사용하는 로직(초기화 로직)이 있다면? url은 아직 null인 상태
                    NetworkClient networkClient = new NetworkClient();
                    // 수정자 주입 (의존관계 주입)
                    networkClient.setUrl("https://hello-spring.dev");
                    return networkClient;
                }
            }
        }
        ```
        
        > [출력]
        생성자 호출, url = null
        connect : null
        call : null message = 초기화 연결 메시지
        > 
        - 스프링 빈의 url이 제대로 초기화되지 않음
            - 스프링 빈이 외부 수정자 주입을 통해 의존관계를 주입 받는데 `의존관계를 주입 받기 전에 생성자가 먼저 호출됨`
- `스프링 빈 라이프사이클`
    
    > 스프링 컨테이너 생성 → 스프링 빈 생성 → `의존관계 주입` → `초기화 콜백` → 사용 → `소멸 전 콜백` → `스프링 종료`
    > 
    - 스프링 빈이 **생성자 주입**을 받는 경우
        
        → 스프링 **빈 생성 단계에서 의존관계 주입**이 일어나기 때문에 **문제가 발생하지 않음**
        
    - 그럼 무조건 생성자 주입으로만 의존관계를 주입하면 되는것 아닌가?
        - `객체(빈)`의 **생성과 초기화**를 **분리**
            - 객체 생성
                - 파라미터 받기
                - 메모리 할당해 객체 생성
            - 초기화
                - 외부 컨넥션을 연결 → 무거운 작업일 경우 분리 효과 더 큼
        - `생성자 주입`은 **객체 내부에 값을 셋팅하는 정도**로만 사용함
        - **무거운 작업**을 하거나 **외부와 연결**을 해야하는 경우는 `생성과 초기화를 분리`해서 사용하는게 **유지보수하기가 훨씬 좋음**
    - **싱글톤 빈**은 스프링 컨테이너가 **종료되기 직전**에 **소멸 전 콜백**이 일어남
    - 생명주기가 짧은 빈의 경우 컨테이너가 종료되기 전에 소멸 전 콜백이 일어남 (8장 스코프에서 자세히)
- 스프링은 3가지 `빈 생명주기 콜백`을 지원함
    1. InitializingBean, DisposableBean 
        
        → 거의 사용x
        
    2. 설정 정보에서 **초기화, 종료 메소드 지정** 
        
        → 외부 라이브러리를 사용할 경우
        
    3. `@PostConstruct`, `@PreDestroy` 어노테이션 
        
        → 제일 많이 사용
        

## 7.2 인터페이스 InitializingBean, DisposableBean

```java
public class NetworkClient implements InitializingBean, DisposableBean {    
		
		private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
				//connect();
				//call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
    }

    public void connect() {
    }

    public void call(String message) {
    }

    public void disconnect() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception {
        disconnect();
    }
}
```

```java
@Test
public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    ac.getBean(NetworkClient.class);
    ac.close();
}

@Configuration
static class LifeCycleConfig {

    @Bean
    public NetworkClient networkClient() {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setUrl("https://hello-spring.dev");
        return networkClient;
    }
}
```

> [출력]
생성자 호출, url = null  
connect: [http://hello-spring.dev](http://hello-spring.dev/)  
call: [http://hello-spring.dev](http://hello-spring.dev/) message = 초기화 연결 메시지  
13:24:49.043 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing NetworkClient.destroy  
close + [http://hello-spring.dev](http://hello-spring.dev/)
> 
- 진행 순서
    1. 스프링 컨테이너 생성
    2. `스프링 빈 생성` → 생성자 호출
    3. setUrl() 호출 → `의존관계 주입`
    4. afterPropertiesSet()  호출 → `초기화 콜백`
    5. 빈 사용
    6. destroy() 호출 → 소멸 전 콜백
    7. 스프링 종료
        
        ⇒ 의존관계 주입 후 초기화 됨
        
- `단점`
    - **스프링 전용 인터페이스** → 스프링에 의존적
    - 이미 정의된 인터페이스를 구현해야 하므로 **메서드 이름을 변경할 수 없음**
    - **외부 라이브러리**에 **적용할 수 없음**
        
        ⇒ 거의 사용 x
        

## 7.3 초기화 메소드, 소멸 메소드 지정

```java
public class NetworkClient {

    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
```

```java
    @Configuration
    static class LifeCycleConfig {

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }
```

- 스프링 빈이 스프링에 의존하지 않음
- **메소드 이름을 자유롭게 사용**할 수 있음
- 코드를 고칠 수 없는 외부 라이브러리에서도 초기화, 종료 메소드를 사용할 수 있음

## 7.4 @PostConstruct & @PreDestroy

```java
public class NetworkClient {

    @PostConstruct
    public void init() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }
}
```

```java
    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }
```

- 종료 메소드, 초기화 메소드에 어노테이션만 붙이면 됨
- **가장 편리**하고 컴포넌트 스캔과도 잘 어울리므로 **권장하는 방법**
- javax 자바 표준을 사용함 → 스프링에 의존적이지 않음
- 유일한 단점은 코드를 고칠 수 없는 외부 라이브러리인 경우 어노테이션을 붙일 수 없기 때문에 사용 불가
    - 이 경우 메소드 지정 방법으로 해결하면 됨

# 8. 빈 스코프

## 8.1 빈 스코프

- 빈 스코프
    - 빈이 존재할 수 있는 범위
- 스프링이 지원하는 빈 스코프
    1. `싱글톤 스코프` (default)
        - 스프링 컨테이너의 시작부터 종료까지 유지되는 스코프
            - 가장 넓은 범위의 스코프
    2. `프로토타입 스코프`
        - 스프링 컨테이너가 빈 객체 생성 & 의존관계 주입까지 관여
            - 이 후로는 관리 x
            - 매우 짧은 범위의 스코프
    3. 웹 관련 스코프
        - `request`
            - 웹 요청이 들어오고 나갈때까지 유지되는 스코프
        - session
        - application
- 사용 방법
    - 빈으로 생성될 코드에 @Scope(”…”)로 지정
    - default → 싱글톤 스코프
        
        ```java
        @Scope("prototype")
        @Component
        public class PrototypeBean {
        }
        ```
        
        ```java
        @Scope("prototype")
        @Bean
        public MemberService memberService() {
        }
        ```
        

## 8.2 싱글톤 스코프

- 스프링 컨테이너의 시작부터 종료까지 유지되는 스코프
    - 가장 넓은 범위의 스코프
- default
    - 지금까지 사용한 스프링 빈은 싱글톤 빈이었음
- 스프링 컨테이너에서 조회 시
    - 항상 **같은 인스턴스**를 반환
        
        ![Untitled](![Untitled 0](https://user-images.githubusercontent.com/87421893/169703529-1f817cdf-6c9c-4c12-9b47-8a16ead2deb3.png)
)
        

## 8.3 프로토타입 스코프

- 스프링 컨테이너가 빈 객체 생성 & 의존관계 주입까지 관여
    - 이 후로는 관리 x
    - 스프링 빈을 받은 클라이언트가 관리해야 함
        - @PreDestroy 메소드가 자동으로 호출되지 않음. 직접 호출해야 함
    - 매우 짧은 범위의 스코프
- 스프링 컨테이너에서 조회 시
    - 항상 **새로운 인스턴스**를 생성해서 반환
        
        ![Untitled](![Untitled 1](https://user-images.githubusercontent.com/87421893/169703538-8c2c145f-de55-4268-a6f1-1098e7fffdb1.png)

        
        ![Untitled](![Untitled 2](https://user-images.githubusercontent.com/87421893/169703549-cd69ddbf-f96c-47fc-a225-682e370b0eed.png)
)
        
- 사용 이유
    - 스프링 빈을 사용할 때마다 의존관계 주입이 완료된 새로운 인스턴스가 필요한 경우
    - 실무에서 대부분 싱글톤 스코프로 해결되므로 자주 사용하진 않음

```java
@Test
public void prototypeBeanFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

    PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
    PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

    System.out.println("bean1 = " + bean1);
    System.out.println("bean2 = " + bean2);

    // destroy 메소드를 사용자가 직접 호출하지 않으면 빈이 자동으로 소멸되지 않음
    bean1.destroy();

    ac.close();

    assertThat(bean1).isNotEqualTo(bean2);
}

@Scope("prototype")
static class PrototypeBean {

    @PostConstruct
    public void init() {
        System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("PrototypeBean.destroy : " + this);
    }
}
```

> [출력]
> 
> 
> `PrototypeBean.init
> PrototypeBean.init`
> bean1 = hello.core.scope.PrototypeTest$PrototypeBean@`5f9be66c`
> bean2 = hello.core.scope.PrototypeTest$PrototypeBean@`3abada5a`
> PrototypeBean.destroy : hello.core.scope.PrototypeTest$PrototypeBean@5f9be66c
> 10:51:11.498 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing
> 
- 스프링 빈 조회 시 인스턴스 생성 후 반환
    - 각 요청마다 하나씩 생성되므로 생성자가 2번 호출됨을 확인
- 2번의 요청으로 생성된 프로토타입 스코프 빈은 서로 다름
- 스프링 컨테이너는 클라이언트에게 인스턴스 반환 후 더 이상 관리하지 않음
    - 종료 메소드를 직접 호출한 bean1만 destroy() 메소드가 실행됨

## 8.4 프로토타입 & 싱글톤 빈을 같이 사용할 때 문제점

### 1. `싱글톤 빈`이 **프로토타입 빈을 필드로 가지는 경우**

```java
@Test
public void singletonClientUsePrototype() {
    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

    ClientBean bean1 = ac.getBean(ClientBean.class);
    ClientBean bean2 = ac.getBean(ClientBean.class);

    int count1 = bean1.logic();     // 1
    int count2 = bean2.logic();     // 2

    assertThat(bean1.prototypeBean).isEqualTo(bean2.prototypeBean);
    assertThat(count1).isNotSameAs(count2);
}

@RequiredArgsConstructor
static class ClientBean {

    private final PrototypeBean prototypeBean; // 프로토타입 스코프 빈

    public int logic() {
        prototypeBean.addCount();
        return prototypeBean.getCount();
    }
}
```

- 싱글톤 빈은 스프링 컨테이너 시작 시 생성되는데 이 때 생성자 주입으로 프로토타입 빈을 요청하게 됨
- 스프링 컨테이너는 프로토타입 빈을 생성해서 반환해주고 싱글톤 빈 내부에 저장됨 (정확히는 프로토타입 빈의 참조값 저장)
- 이 후에 싱글톤 빈 조회 시 이미 생성된 싱글톤 빈을 조회하게 됨
    - 이미 생성된 싱글톤 빈을 조회하므로 프로토타입 빈을 요청하지 않음
    - 새로운 프로토타입 빈 인스턴스가 생성되지 않음
        - 프로토타입 빈은 빈 요청 때마다 새로운 별개의 인스턴스가 생성되어 각각 다른 빈을 사용하기 위해 사용함
        - 위의 경우는 같은 프로토타입 빈 인스턴스를 사용하게되어 원하는 결과를 얻지 못함

### 2. 해결 방법

1. **스프링 컨테이너에 직접 요청**
    
    ```java
    @RequiredArgsConstructor
    static class ClientBean {
    
        private final AnnotationConfigApplicationContext ac;
    
        public int logic() {
            // 의존관계를 외부에서 주입 받는게 아니라 직접 필요한 의존관계를 찾음
            // -> Dependency Lookup (DL)
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    ```
    
    - 스프링 빈의 **필드로 스프링 컨테이너를 가짐**
    - 스프링 내부 로직에서 스**프링 컨테이너를 직접 사용해** `의존관계를 찾음` (`DL`, Dependency Lookup)
        - 싱글톤 빈은 1개지만 로직을 실행할 때마다 프로토타입 빈을 요청하므로 서로 다른 프로토타입 빈을 얻게 됨
    - 단점
        - **스프링 컨테이너에 종속적인 코드**가 됨
        - **테스트가 어려워짐**
    
    > 프로토타입 빈을 대신 찾아주는, 딱 DL 기능만 있는 무언가가 필요함
    → Provider
    > 
2. `ObjectProvider`
    - **스프링 컨테이너에 독립적**이면서 **DL 기능만 제공**하는 클래스
        
        ```java
        @RequiredArgsConstructor
        static class ClientBean {
        
            private final ObjectProvider<PrototypeBean> provider;
        
            public int logic() {
                PrototypeBean prototypeBean = provider.getObject();
                prototypeBean.addCount();
                return prototypeBean.getCount();
            }
        }
        ```
        
        - 스프링 컨테이너를 통해 해당 빈을 찾아서 반환 (**DL**)
        - 스프링에서 제공하는 기술 → **스프링에 의존적**
        - 만약 **스프링이 아닌 다른 컨테이너**에서 DL이 필요한 경우
            - **Provider**를 사용해야 함
    - ObjectFactory 클래스의 상속을 받음
        - 편의기능이 추가된 것
    - 별도의 라이브러리는 필요없지만 스프링에 의존적
        - 스프링 컨테이너에서만 사용 가능
3. `JSR-330 Provider`
    - **스프링에 의존하지 않는 Provider**
        - 자바 표준을 사용 (javax.inject.Provider)
        - gradle에 라이브러리 추가해야 함
            - `javax.inject:javax.inject:1`
    - 별도의 라이브러리가 필요하지만 스프링에 의존적이지 않음
        - **다른 컨테이너에서도 사용 가능**

> 보통 스프링 컨테이너에서 사용하므로 ObjectProvider를 사용함
> 

> 사실 실무에서 싱글톤 빈으로 대부분 해결 가능하기 때문에 프로토타입 빈을 사용하는 일을 매우 드묾
DL은 프로토타입에만 사용하는 것은 아니므로 **Provider에 대한 이해**를 하고 있을 것
> 

## 8.5 웹 스코프

- 특징
    - **웹 환경에서만 동작**
    - 스프링 컨테이너가 스코프의 **종료 시점까지 관리**
        - 종료 메소드 자동 호출됨
- 종류
    - `request`
        - HTTP 요청이 들어올 때 부터 나갈때까지 유지되는 스코프
        - HTTP 요청 별 인스턴스가 생성
    - session
        - HTTP 세션과 동일한 생명주기를 가짐
    - application
        - ServletContext와 동일한 생명주기를 가짐
    - websocket
        - 웹 소켓과 동일한 생명주기를 가짐
    
    → request만 알면 나머지는 다 비슷
    

## 8.6 request 스코프

### 1. request 스코프

- **HTTP 요청이 들어올 때 부터 나갈때까지 유지**되는 스코프
- 웹 환경에서만 동작하므로 **라이브러리** 필요
    - implementation 'org.springframework.boot:spring-boot-starter-web'
    - 이 라이브러리를 추가하면 스프링 부트가 **내장 톰캣 서버**와 스프링을 함께 실행시킴
    - AnnotationConfigApplicationContext → AnnotationConfigServletWebServerApplicationContext 기반으로 애플리케이션 구동
- `HTTP 요청 별 인스턴스가 생성`
    
    ![Untitled](![Untitled 3](https://user-images.githubusercontent.com/87421893/169703563-c286ca83-d1a5-4186-a740-6961cafd2958.png)
)
    
- 동시에 여러 HTTP 요청이 오는 경우
    - 요청 별 로그를 남기고 싶을 때 사용하면 효과적
        
        ```java
        @Component
        @Scope(value = "request")
        public class MyLogger {
        
            private String uuid; // 각 http 요청마다 다른 값
            private String requestURL;
        
            public void log(String message) {
            }
        
            public void setRequestURL(String requestURL) {
            }
        
            @PostConstruct
            public void init() {
                uuid = UUID.randomUUID().toString();
            }
        
            @PreDestroy
            public void close() {
            }
        }
        ```
        
        ```java
        @Service
        @RequiredArgsConstructor
        public class LogDemoService {
        
            private final MyLogger myLogger;
        
            public void logic(String id) {
                myLogger.log("service id : " + id);
            }
        }
        ```
        
        ```java
        @Controller
        @RequiredArgsConstructor
        public class LogDemoController {
        
            private final LogDemoService logDemoService;
            private final MyLogger myLogger;
        
            @RequestMapping("log-demo")
            @ResponseBody
            public String logDemo(HttpServletRequest request) {
                String requestURL = request.getRequestURL().toString();
        
                myLogger.setRequestURL(requestURL);
                myLogger.log("controller test");
                logDemoService.logic("testID");
                return "ok";
            }
        }
        ```
        
        - 위의 코드는 `에러`가 남
            - Controller, Service 클래스가 **request 스코프 빈을 필드로 가짐**
            - `request 스코프 빈`은 **HTTP 요청이 와야 생성되는 빈**
            - @RequiredArgsConstructor
                - final 필드를 모아 의존관계를 주입받는 생성자를 만듦
                - **웹 스코프 빈은 아직 생성되지 않았는데 의존관계 주입을 받으려고 시도함**
                    
                    → 에러 발생
                    
        - 해결 방법
            1. Provider
            2. proxyMode

### 2. Provider

- Controller, Service 클래스에 request 스코프 빈이 아닌 **Provider를 필드로** 가지게 함
    - Controller, Service 스프링 빈이 생성될 때 **request 스코프 빈을 조회하지 않음**
    
    ```java
    @Service
    @RequiredArgsConstructor
    public class LogDemoService {
    
        private final ObjectProvider<MyLogger> provider;
    
        public void logic(String id) {
            MyLogger myLogger = provider.getObject();
            myLogger.log("service id : " + id);
        }
    }
    ```
    
    ```java
    @Controller
    @RequiredArgsConstructor
    public class LogDemoController {
    
        private final LogDemoService logDemoService;
        private final ObjectProvider<MyLogger> provider;
    
        @RequestMapping("log-demo")
        @ResponseBody
        public String logDemo(HttpServletRequest request) {
            String requestURL = request.getRequestURL().toString();
            MyLogger myLogger = provider.getObject();
    
            myLogger.setRequestURL(requestURL);
            myLogger.log("controller test");
            logDemoService.logic("testID");
            return "ok";
        }
    }
    ```
    
    - HTTP 요청이 오면 (log-demo URL 요청) 해당 메소드가 실행될 때 Provider가 request 스코프 빈을 조회하여 의존관계를 주입 (DL)
        - HTTP 요청 → request 스코프 빈 생성
        - Provider가 조회
    - `핵심`
        - request 스코프 빈이 생성될 때까지 **빈의 조회를 지연** 시킴!

### 3. proxyMode

- request 스코프 빈을 상속받은 가짜 프록시 빈을 미리 주입함
    
    ```java
    @Component
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public class MyLogger {
    		내부 동일    
    }
    ```
    
    - TARGET_CLASS → 클래스에 적용 시
    - TARGET_INTERFACE → 인터페이스에 적용 시
    
    ```java
    @Service
    @RequiredArgsConstructor
    public class LogDemoService {
    
        private final MyLogger myLogger;
    
        public void logic(String id) {
            myLogger.log("service id : " + id);
        }
    }
    ```
    
    ```java
    @Controller
    @RequiredArgsConstructor
    public class LogDemoController {
    
        private final LogDemoService logDemoService;
        private final MyLogger myLogger;
    
        @RequestMapping("log-demo")
        @ResponseBody
        public String logDemo(HttpServletRequest request) {
            String requestURL = request.getRequestURL().toString();
    
            myLogger.setRequestURL(requestURL);
            myLogger.log("controller test");
            logDemoService.logic("testID");
            return "ok";
        }
    }
    ```
    
    - Controller, Service 클래스를 처음 에러가 발생한 코드와 똑같이 작성해도 오류가 안남
        - **필드로 request 스코프 빈**을 가지고 있어도 **생성 시점에 의존 관계를 주입받을 수 있기 때문**
    - `동작 원리`
        1. 스프링 컨테이너가 `CGLIB 라이브러리`를 통해 request 스코프 빈 클래스를 상속받은 `가짜 프록시 객체를 생성함`
            - 가짜 프록시 객체 이름은 **request 스코프 빈 이름과 동일** (myLogger)
            - 가짜 프록시 객체에는 **request 스코프 빈을 요청할 수 있는 위임 로직**이 들어있음
            - 결국 가짜 프록시 객체는 스프링 컨테이너 시작 시점에 생성되므로 **request 스코프가 아니라 싱글톤 스코프**
        2. Controller, Service 빈 생성 시 `가짜 프록시 객체를 주입함`
        3. `HTTP 요청`이 오면 `가짜 프록시 객체의 메소드가 호출`됨 (myLogger.logic())
        4. 이 메소드가 `진짜 request 스코프 빈의 myLogger.logic()을 호출`함
            - HTTP 요청 → request 스코프 빈 생성
            - 가짜 프록시 객체가 진짜 request 스코프 빈 요청
    - 어노테이션만 약간 바꿨을 뿐인데 원본 객체 대신 프록시 객체를 사용함
        - 프록시 객체가 원본 객체를 상속받은 객체이기에 가능
        - **다형성을 활용한 DI 컨테이너의 힘!**
    - `핵심`
        - request 스코프 빈이 생성될 때까지 **빈의 조회를 지연** 시킴!
    
    > Provider, proxyMode 둘 다 request 스코프 빈이 생성될 때까지 빈의 조회를 지연시켜서 문제를 해결함
    >
