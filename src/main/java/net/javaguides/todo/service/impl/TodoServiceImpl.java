package net.javaguides.todo.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.TodoRepository;
import net.javaguides.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        /*
convert TodoDto into Todo Jpa entity
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
*/

        Todo todo = modelMapper.map(todoDto, Todo.class);

        //Todo Jpa entity
        Todo savedTodo = todoRepository.save(todo);

         /*
Convert save Todo Jpa entity object into TodoDto object
        TodoDto savedTodoDto = new TodoDto();
        savedTodoDto.setId(savedTodo.getId());
        savedTodoDto.setTitle(savedTodo.getTitle());
        savedTodoDto.setDescription(savedTodo.getDescription());
        savedTodoDto.setCompleted(savedTodo.isCompleted());
*/
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                new ResourceNotFoundException("Todo not exists with given ID" + todoId));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());

    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todoDto) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not exists with given ID" + todoId));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not exists with given ID" + todoId));
        todoRepository.deleteById(todoId);
    }

    @Override
    public TodoDto completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not exists with given ID" + todoId));

        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Todo not exists with given ID" + todoId));

        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
