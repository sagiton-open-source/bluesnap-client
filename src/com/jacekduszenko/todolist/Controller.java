package com.jacekduszenko.todolist;

import com.jacekduszenko.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Controller {
private List<TodoItem> todoItems;

@FXML
private ListView<TodoItem> todolistview;

@FXML
private TextArea textArea;

@FXML
private Label deadlineLabel;

public void initialize() {
TodoItem item1=new TodoItem("Mail birthday card","Buy a 30th birthday card for John",
        LocalDate.of(2017, Month.SEPTEMBER,25));

    TodoItem item2=new TodoItem("My sweet GF's birthday!","Give her a fancy gift!",
            LocalDate.of(2017, Month.SEPTEMBER,5));

    TodoItem item3=new TodoItem("Mail birthday card","Buy a 30th birthday card for John",
            LocalDate.of(2017, Month.DECEMBER,04));

    TodoItem item4=new TodoItem("My birthday","XD",
            LocalDate.of(2017, Month.AUGUST,12));

    TodoItem item5=new TodoItem("Mail birthday card","Buy a 30th birthday card for John",
            LocalDate.of(2017, Month.APRIL,25));


    todoItems=new ArrayList<TodoItem>();
    todoItems.add(item1);
    todoItems.add(item2);
    todoItems.add(item3);
    todoItems.add(item4);
    todoItems.add(item5);

    todolistview.getItems().setAll(todoItems);
    todolistview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
}

    @FXML
    public void handleClickListView() {

TodoItem item= todolistview.getSelectionModel().getSelectedItem();
        StringBuilder sb=new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        //It's temporary! Just delete the item when the time is over. Talking about negative numbers in days.
        Date currentDate = new Date();
        java.util.Date deadline = java.sql.Date.valueOf(item.getDeadline());
        sb.append("    days left: "+ getDateDiff(currentDate,deadline,TimeUnit.DAYS));
        textArea.setText(sb.toString());
       // System.out.println(LocalDate.now().until(item.getDeadline()).getDays());


    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }




}
