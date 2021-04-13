package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.order.QOrder;
import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import ShallWe.Refactoring.entity.tag.QTag;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderSearchTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TagRepository tagRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void OrderSearchByTag() {
        List<OrderResponse> orderResponses = orderRepository.searchByTagName("음식");
    }
}

