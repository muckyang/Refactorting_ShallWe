package ShallWe.Refactoring.controller;

import ShallWe.Refactoring.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    TagRepository tagRepository;

    @PostMapping("order")
    public String postOrder(){


        return "orderMake";
    }
}
