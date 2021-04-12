package ShallWe.Refactoring;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.partyMember.PartyMember;
import ShallWe.Refactoring.entity.user.User;
import ShallWe.Refactoring.repository.order.OrderRepository;
import ShallWe.Refactoring.repository.partyMember.PartyMemberRepository;
import ShallWe.Refactoring.repository.tag.TagRepository;
import ShallWe.Refactoring.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PartyMemberTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PartyMemberRepository partyMemberRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    public void before() {
        logger.trace("=======    START TEST    =======");
    }

    @Test
    public void findPartyMember() throws Exception {
        Optional<Order> order = orderRepository.findById(1L);
        Optional<User> member = userRepository.findById(1L);

        if (order.isPresent() && member.isPresent()) {
            Optional<PartyMember> partyMember = partyMemberRepository.findByOrderAndMember(order.get(), member.get());
            if (partyMember.isPresent()) {
                logger.info(partyMember.get().toString());
            } else
                throw new NullPointerException("null");
        } else
            throw new NullPointerException("null");
    }
}
