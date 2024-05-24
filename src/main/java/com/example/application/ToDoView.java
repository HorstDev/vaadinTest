package com.example.application;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class ToDoView extends VerticalLayout {
    private TodoRepo todoRepo;

    public ToDoView(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;

        var taskField = new TextField();
        var button = new Button("Добавить");
        var todos = new VerticalLayout();

        todos.setPadding(false);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);

        button.addClickListener(click -> {
           var todo = todoRepo.save(new Todo(taskField.getValue()));
           todos.add(createCheckbox(todo));
        });

        todoRepo.findAll().forEach(todo -> todos.add(createCheckbox(todo)));

        add(
                new H1("Добавьте"),
                new HorizontalLayout(taskField, button),
                todos
        );
    }

    private Component createCheckbox(Todo todo) {
        return new Checkbox(todo.getTask(), todo.isDone(), event -> {
            todo.setDone(event.getValue());
            todoRepo.save(todo);
        });
    }
}
