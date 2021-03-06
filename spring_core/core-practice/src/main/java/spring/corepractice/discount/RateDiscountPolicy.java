package spring.corepractice.discount;

import org.springframework.stereotype.Component;
import spring.corepractice.member.Grade;
import spring.corepractice.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        }

        return 0;
    }
}
