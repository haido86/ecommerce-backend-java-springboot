package com.rest_api.fs14backend.todo;

import com.rest_api.fs14backend.category.Category;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
  public Todo toTodo(TodoDTO todo, Category category){
    return new Todo(todo.getTitle(), todo.isCompleted(), category);
  }
}
