package ShallWe.Refactoring.service;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public void createTags(Order order, List<String> tags){
        for(String name : tags) {
            Tag tag = new Tag(name);
            tag.setOrder(order);
            tagRepository.save(tag);
        }
    }
}
