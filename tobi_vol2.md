# Chap 02: 데이터 액세스 기술 

- Declarative Transaction의 `Transaction Propagation`기능 덕분에 여러 메소드를 조합하여 하나의 트랜잭션에서 동작하게 만들 수 있는 것임.
> Declarative Transaction은 EJB의 가장 매력적인 기능이었으나, 스프링에서 POJO방식의 코드로 이 Declarative Transaction을 구현함.

### 2.6.3 트랜잭션 속성
##### Transaction Propagation(트랜잭션 전파): propagation
- `REQUIRED`: (디폴트) 미리 시작된 트랜잭션이 **있으면 참여, 없으면 새로 시작.**
- `SUPPORTS`: 이미 시작된 트랜잭션이 **있으면 참여, 없으면 트랜잭션 없이 진행**
- `MANDATORY`: 이미 시작된 트랜잭션이 **있으면 참여, 트랜잭션이 없으면 Exception 발생.** 
   - 독립적으로 트랜잭션을 진행하면 안되는 경우에 사용.
- `REQUIRES_NEW`: **Always 새로운 독립적인 트랜잭션 시작.**, 이미 트랜잭션이 있으면 그 트랜잭션을 보류시켜놓음.
- `NOT_SUPPORTED`: 트랜잭션을 **사용안함**. 이미 트랜잭션이 있으면 그 트랜잭션을 보류시켜놓음.
- `NEVER`: 트랜잭션을 사용하지 **않도록 강제.** 이미 진행 중인 트랜잭션이 존재하면 안됨.
- `NESTED`: 이미 진행중인 트랜잭션이 있으면 `중첩 트랜잭션(트랜잭션 안의 트랜잭션)`을 시작함.
   - `REQUIRES_NEW`와는 다름.
   - 중첩 트랜잭션은 자신의 부모의 커밋/롤백엔 영향을 받으나 중첩 트랜잭션 자신의 커밋/롤백은 부모 트랜잭션에 영향을 주지 못함.