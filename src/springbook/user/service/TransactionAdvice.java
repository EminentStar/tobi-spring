package springbook.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
  private PlatformTransactionManager transactionManager; // 트랜잭션 기능을 제공하는 데 필요한 트랜잭션 매니저

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    /*
    * getTransaction()이 항상 트랜잭션을 새로 시작하는 것이 아님.
    *
    * 트랜잭션 전파 속성과 현재 진행 중인 트랜잭션이 존재하는지 여부에 따라서 새로운 트랜잭션을 시작할 수동 있고,
    * 이미 진행중인 트랜잭션에 참여하기만 할 수도 있음.
    *
    * 진행중인 트랜잭션에 참여하는 경우는 트랜잭션 경계의 끝에 서 트랜잭션을 커밋시키지도 않음.
    * 최초로 트랜잭션을 시작한 경계까지 정상적으로 진행돼야 비로소 커밋될 수 있음.
    * */
    TransactionStatus status =
      this.transactionManager.getTransaction(new DefaultTransactionDefinition());

    try {
      // 콜백을 호출해서 타깃의 메소드를 실행함. 타깃 메소드 호출 전후로 필요한 부가기능을 넣을 수 있음.
      // 경우에 따라서 타깃이 아예 호출되지 않게 하거나 재시도를 위한 반복적인 호출도 가능함.
      Object ret = invocation.proceed();
      this.transactionManager.commit(status);
      return ret;
    } catch (RuntimeException e) { // JDK 다이내믹 프록시가 제공하는 Method와는 달리 스프링의 MethodInvocation을 통한 타깃 호출은 예외가 포장되지 않고 타깃에서 보낸 그대로 전달됨.
      this.transactionManager.rollback(status);
      throw e;
    }
  }
}
