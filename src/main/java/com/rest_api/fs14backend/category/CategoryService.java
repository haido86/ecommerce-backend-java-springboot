package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  public Category createOne(Category category) {
    return categoryRepository.save(category);
  }

  public Category findById(UUID categoryId) {
    return categoryRepository.findById(categoryId).orElse(null);
  }
}
