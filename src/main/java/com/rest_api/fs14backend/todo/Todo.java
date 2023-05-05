package com.rest_api.fs14backend.todo;

import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "todo")
@Table(name = "todo")
@Data
@NoArgsConstructor
public class Todo {
  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;
  @Column(nullable = false, columnDefinition = "varchar(50)")
  private String title;
  @Column(nullable = false)
  private boolean isCompleted;

  @ManyToOne( optional = false)
  private Category category;
  public Todo(String title, boolean isCompleted, Category category) {
    this.title = title;
    this.isCompleted = isCompleted;
    this.category = category;
  }

}
