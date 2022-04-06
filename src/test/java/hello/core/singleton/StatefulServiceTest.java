package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //singleton은 state속성을 넣으면 안된다!
        //ThreadA: 사용자A 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadA: 사용자B 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int priceA = statefulService1.getPrice();
        System.out.println("priceA = " + priceA);

        //10000원이 나와야 정상이다.
        Assertions.assertThat(priceA).isNotEqualTo(10000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}