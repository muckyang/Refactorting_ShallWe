package ShallWe.Refactoring.service.tag;

import ShallWe.Refactoring.entity.order.Order;
import ShallWe.Refactoring.entity.tag.Tag;
import ShallWe.Refactoring.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public void createTags(Order order, List<String> tags){
        for(String name : tags) {
            Tag tag = new Tag(name);
            tag.setOrder(order);
            tagRepository.save(tag);
        }
    }
}
