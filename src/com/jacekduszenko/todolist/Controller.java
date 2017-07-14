package com.jacekduszenko.todolist;

import com.jacekduszenko.todolist.datamodel.ToDoData;
import com.jacekduszenko.todolist.datamodel.TodoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.awt.*;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


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

@FXML
private ContextMenu simpleMenu;

@FXML
private ToggleButton filter;

private FilteredList<TodoItem> filteredList;
public void initialize() {
    simpleMenu=new ContextMenu();
    MenuItem deleteMenuItem = new MenuItem("Delete");


    deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
TodoItem item=todolistview.getSelectionModel().getSelectedItem();
deleteItem(item);
        }
    });


simpleMenu.getItems().addAll(deleteMenuItem);

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



        filteredList = new FilteredList<TodoItem>(ToDoData.getInstance().getTodoItems(), new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem todoItem) {
                return true;
            }
        });

    SortedList<TodoItem> sortedList=new SortedList<TodoItem>(filteredList, new Comparator<TodoItem>() {
        @Override
        public int compare(TodoItem o1, TodoItem o2) {
            return o1.getDeadline().compareTo(o2.getDeadline());
        }
    });

//    todolistview.setItems(ToDoData.getInstance().getTodoItems());
    todolistview.setItems(sortedList);
    todolistview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    todolistview.getSelectionModel().selectFirst();

    todolistview.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
        @Override
        public ListCell<TodoItem> call(ListView<TodoItem> param) {
            ListCell<TodoItem> cell = new ListCell<TodoItem>() {

                @Override
                protected void updateItem(TodoItem item, boolean empty) {
                    super.updateItem(item, empty);
if(empty) {
    setText(null);
}
else {
    setText(item.getShortDescription());
    if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
        setTextFill(Color.RED);
    }
    else if(item.getDeadline().equals(LocalDate.now().plusDays(1))) {
        setTextFill(Color.BLUE);
    }
}

                }
            };
            cell.emptyProperty().addListener(
                    (obs,wasEmpty, isnowEmpty) -> {
                      if(isnowEmpty) {
                          cell.setContextMenu(null);
                      }
                      else {
                          cell.setContextMenu(simpleMenu);
                      }
                    }


            );

return  cell;
        }
    });
}

@FXML
public void showNewItemDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.initOwner(MainBorderPane.getScene().getWindow());
    dialog.setTitle("Add a new to-do item");

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("todoItemDialog.fxml"));
    try {

        dialog.getDialogPane().setContent(loader.load());
    } catch (IOException e) {
        System.out.println("Couldn't load the dialog");
        e.printStackTrace();
    }
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        DialogController controller = loader.getController();
        TodoItem newItem = controller.processResults();
        todolistview.getSelectionModel().select(newItem);
    }
}

@FXML
    public void HandleKeyPressed(KeyEvent event){
TodoItem item=todolistview.getSelectionModel().getSelectedItem();
if(item!=null) {
    if(event.getCode().equals(KeyCode.DELETE)) {
        deleteItem(item);
    }
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
    public void deleteItem(TodoItem item) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo item");
        alert.setHeaderText("Delete item "+item.getShortDescription());
        alert.setContentText("The item will be deleted forever");
        Optional<ButtonType> result =alert.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    public void handlefilter() {
        TodoItem selectedItem=todolistview.getSelectionModel().getSelectedItem();

        if(filter.isSelected()) {
filteredList.setPredicate(new Predicate<TodoItem>() {
    @Override
    public boolean test(TodoItem todoItem) {
        return todoItem.getDeadline().equals(LocalDate.now());
    }

});
            if(filteredList.isEmpty()) {
                textArea.clear();
                deadlineLabel.setText("");
            }
            else if(filteredList.contains(selectedItem)){
                todolistview.getSelectionModel().select(selectedItem);
            }
            else {
                todolistview.getSelectionModel().selectFirst();
            }
        }
        else {
            filteredList.setPredicate(new Predicate<TodoItem>() {
                @Override
                public boolean test(TodoItem todoItem) {
                    return true;
                }
            });
            todolistview.getSelectionModel().select(selectedItem);
        }
    }
    @FXML
public void exitHandler() {
        Platform.exit();
}


}
