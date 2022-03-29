package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration 어노테이션을 쓰면 해당클래스의 자식클래스로 클래스이름$$CGLIB타입을 빈 컨테이너에 등록시킨다.
// 그러고 나서 바이트 코드를 변경해서 싱글톤을 만족시켜주는 것!
// @Configuration을 주석처리하면 call AppConfig.memberRepository 3번 호출

//call AppConfig.memberService
//call AppConfig.memberRepository
//call AppConfig.memberRepository
//call AppConfig.orderService
//call AppConfig.memberRepository

//이렇게 되어서 싱글톤을 만족시키지 못할 것 같지만
//실제로
//call AppConfig.memberService
//call AppConfig.memberRepository
//call AppConfig.orderService
//이렇게 찍히고, 스프링에서 싱글톤을 만족시켜준다!

@Configuration
public class AppConfig { //application에 대한 환경설정(배우 섭외)는 여기서 다 한다.

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
