# design-pattern-tutorial-project

> **Head First** Design Pattern

***

- Using
  - [x] StrategyPattern
  - [x] DecoratorPattern
  - [x] CompoundPattern
  - [x] TemplateMethodPattern
  - [x] FactoryPattern
  - [x] SingletonPattern

***

- Learning
  - [ ] ProxyPattern
  - [ ] ObserverPattern
  - [ ] StatePattern

***

<br/>

## Description

**매장**(shop)에 전시된 **물건**(product)들을 **손님**(user)이 **장바구니**(cart)에 담고 **결제**(payment)방식을 선택해 구매하는 기능을 구현

<br/>

***

## Pattern

- MVC (CompoundPattern)

  - 패키지 구성
    - cart
      - classes
      - controller
      - model
      - view
    - product
      - classes
      - controller
      - model
      - view
    - user
      - classes
      - controller
      - model
      - View

  - 상세
    - view 
      - 웹페이지를 활용하지 않고 콘솔에 정보를 출력하기 위해 ViewClass를 생성하여 표현
    - controller
      - view의 요청에 따라 dao에서 제공하는 메소드를 호출
    - model
      - 데이터 베이스에 접근하여 컨트롤러의 요청에 따른 비즈니스 로직을 처리

***

- StrategyPattern
  - 패키지 구성
    - payment
      - Card
      - Cash
      - Payment
  - 상세
    - Payment
      - 결제 방식을 캡슐화 하기위한 인터페이스
    - Card, Cash
      - Payment 인터페이스를 구현하는 구현클래스
  - 정리
    - 알고리즘을 런타임중에 선택할 수 있다.

***

- DecoratorPattern
  - 패키지 구성
    - rank
      - Gold
      - None
      - Platinum
      - Rank
      - RankDecorator
      - RankFactory
      - Silver
  - 상세
    - Rank
      - 추상 클래스로서, 기본 기능을 제공하는 랭크인 None과 추가 기능을 제공하는 RankDecorator의 공통 기능을 정의한다.
      - 클라이언트는 Rank를 통해 실제 객체를 사용한다.
    - None
      - 기본 기능을 구현하는 클래스
    - RankDecorator
      - 구체적인 데코레이터는 RankDecorator를 상속받아 각각의 기능을 추가한다. RankDecorator는 공통 기능을 정의한다.
      - RankDecorator는 Rank를 필드로 갖는다. (composition)
      - 생성자 매개변수로 Rank를 초기화 한다.
    - Silver, Gold, Platinum
      - RankDecorator의 하위 클래스로서, 기본 기능에 추가되는 개별적인 기능을 제공한다.
  - 정리
    - Decorator 패턴을 이용하여 추가 기능 조합별로 별도의 클래스를 구현하는 대신 각 추가 기능에 해당하는 클래스의 객체를 구성(composition)하여 추가 기능의 조합을 구현할 수 있다.
  - 참고
    - https://gmlwjd9405.github.io/2018/07/09/decorator-pattern.html

***

- TemplateMethodPattern

  - 패키지 구성
    - user
      - view
        - AdminShopView
        - LoginView
        - MainView
        - NormalUserShopView
        - RegisterView
        - SettingView
        - ShopView
  - 상세
    - LoginView
      - 사용자가 로그인 할때 출력되는 화면
      - 아이디와 비밀번호를 입력받고 만약 관리자라면, 관리자 화면을 출력하게 되고 일반 사용자라면 사용자 화면을 출력한다.
    - ShopView
      - 공통된 작업을 처리하는 부분과 관리자인지 일반사용자인지에 따라 메뉴를 출력하는 selectMenu() 메소드를 포함하는 printShopMenu() 메소드를 제공하는 추상 클래스
      - Hook 메소드를 통해 사용자 id에 따라 WELCOME 메시지를 출력한다.
    - AdminShopView
      - 관리자의 화면을 출력하며, selectMenu() 메소드를 오버라이딩 한다.
    - NormalUserShopView
      - 일반 사용자의 화면을 출력하며, selectMenu() 메소드를 오버라이딩 한다.

- FactoryPattern

  - 패키지 구성

    - rank
      - RankFactory

  - 상세

    - RankFactory

      - rankId에 따른 Rank객체를 생성하는 작업을 RankFactoy 클래스에게 위임하여 Rank 객체를 리턴한다.

      - 인스턴스 생성을 위임함으로써, 클라이언트는 어떤 객체가 생성 되었는지 신경쓰지 않고 rankId에 따른 rank객체를 반환 받기만 하면 된다.

      - ```java
        Rank currentUserRank = rankFactory.getRank(userRankId); 
        /*
        	UserDAO.java 
        	115 line
        */
        ```

***

- SingletonPattern
  - 패키지 구성
    - lib
      - java
        - scanner
          - SingleScanner
  - 상세
    - SingleScanner
      - Scanner 객체를 하나만 만들어 사용하기 위해 사용한 패턴
      - 많은 뷰 클래스들에서 입력을 받는 작업을 scanner 객체 하나만을 사용하기 위함
      - new 키워드를 통해 객체를 생성할 수 없도록 생성자에 접근 지정자를 private 으로 지정
      - 유일한 단일 객체를 반환하기 위해 정적 메소드 생성, 반환할 객체 또한 정적 참조변수가 되어야 한다.
  - 참고
    - https://www.tutorialkart.com/java/singleton-class-in-java/
      - Early Loading, Lazy Loading (thread not safe), Lazy Loading (thread safe)
    - https://asfirstalways.tistory.com/335
      - 멀티스레딩 환경에서의 싱글턴
      - 동기화 (synchronized) 키워드를 통한 멀티스레딩 문제 해결
      - DCL (Double Checking Locking)

