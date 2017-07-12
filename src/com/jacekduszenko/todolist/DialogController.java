package com.jacekduszenko.todolist;

import com.jacekduszenko.todolist.datamodel.ToDoData;
import com.jacekduszenko.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDesc;
    @FXML
    private TextArea longDesc;
    @FXML
    private DatePicker date;


    public TodoItem processResults() {
String shortDescription=shortDesc.getText().trim();
String details= longDesc.getText().trim();
LocalDate deadline = date.getValue();

TodoItem newItem = new TodoItem(shortDescription, details, deadline);
        ToDoData.getInstance().addTodoItem(newItem);
        return newItem;

    }
}
