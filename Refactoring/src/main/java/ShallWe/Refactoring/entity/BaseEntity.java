package ShallWe.Refactoring.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//엔티티에 Auditing 적용하도록 해줌
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    LocalDateTime createTime = LocalDateTime.now();
    protected BaseEntity(){
    }
}
