package ShallWe.Refactoring.repository.order;

import ShallWe.Refactoring.entity.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface OrderRepositoryCustom {
    Page<OrderResponse> getUserPaging(Pageable pageable);
    Slice<OrderResponse> getUserScroll(Pageable pageable);
}
