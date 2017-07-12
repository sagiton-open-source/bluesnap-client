package com.jacekduszenko.todolist;

import com.jacekduszenko.todolist.datamodel.ToDoData;
import com.jacekduszenko.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class Controller {
private List<TodoItem> todoItems;

@FXML
private ListView<TodoItem> todolistview;

@FXML
private TextArea textArea;

@FXML
private Label deadlineLabel;

@FXML
private BorderPane MainBorderPane;

public void initialize() {


        todolistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {

                if(newValue!=null) {
                    TodoItem item = todolistview.getSelectionModel().getSelectedItem();
                    textArea.setText(item.getDetails());
                    StringBuilder sb=new StringBuilder();
                    Date currentDate = new Date();
                    java.util.Date deadline = java.sql.Date.valueOf(item.getDeadline());
                    sb.append("\n days left: "+ getDateDiff(currentDate,deadline,TimeUnit.DAYS));
                    DateTimeFormatter dft= DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    deadlineLabel.setText(dft.format(item.getDeadline())+sb.toString());
                }
            }
        });

    todolistview.getItems().setAll(ToDoData.getInstance().getTodoItems());
    todolistview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todolistview.getSelectionModel().selectFirst();
}

@FXML
public void showNewItemDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(MainBorderPane.getScene().getWindow());
    dialog.setTitle("Add a new to-do item");

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("todoItemDialog.fxml"));
    try{

        dialog.getDialogPane().setContent(loader.load());
    }catch(IOException e) {
        System.out.println("Couldn't load the dialog");
        e.printStackTrace();
    }
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> result= dialog.showAndWait();
    if(result.isPresent() && result.get()==ButtonType.OK) {
        DialogController controller =loader.getController();
        TodoItem newItem=controller.processResults();
        todolistview.getItems().setAll(ToDoData.getInstance().getTodoItems());
        todolistview.getSelectionModel().select(newItem);
        System.out.println("OK pressed");
    } else {
        System.out.println("CANCEL pressed");
    }

}

    @FXML
    public void handleClickListView() {

TodoItem item= todolistview.getSelectionModel().getSelectedItem();
textArea.setText(item.getDetails());

    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }


}
