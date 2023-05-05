package com.rest_api.fs14backend.todo;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryRepository;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

  @Autowired
  private TodoService todoService;
  @Autowired
  private CategoryService categoryService;

  @Autowired
  private TodoMapper todoMapper;

  @PostMapping("/")
  public Todo createOne(@RequestBody TodoDTO todoDTO) {
    UUID categoryId = todoDTO.getCategoryId();
    Category category = categoryService.findById(categoryId);

    Todo todo = todoMapper.toTodo(todoDTO, category);

    return todoService.createOne(todo);
  }

  @GetMapping("/")
  public List<Todo> findAll() {
    return todoService.findAll();
  }

  @GetMapping("/{id}")
  public Todo findById(@PathVariable UUID id) {
    Todo todo = todoService.findById(id);

    if (todo == null) {
      throw new NotFoundException("Todo not found");
    }
    return todo;
  }

  @DeleteMapping("/{id}")
  public void deleteOne(@PathVariable UUID id) {
    todoService.deleteById(id);
  }
}
