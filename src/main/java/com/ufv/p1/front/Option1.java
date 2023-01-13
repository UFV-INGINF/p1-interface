package com.ufv.p1.front;

import com.ufv.p1.back.ColmenarViejoCSV;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Option1 extends VerticalLayout {

    ArrayList<ColmenarViejoCSV> datosCSV;

    HorizontalLayout params = new HorizontalLayout();
    VerticalLayout results = new VerticalLayout();



    public Option1(@Autowired ArrayList<ColmenarViejoCSV> datosCSV) {

        this.datosCSV = datosCSV;
        DatePickerWithPattern startDate = new DatePickerWithPattern();
        startDate.setLabel("Start date");
        startDate.setPattern("dd-MM-yyyy");

        DatePickerWithPattern endDate = new DatePickerWithPattern();
        endDate.setLabel("End date");
        endDate.setPattern("dd-MM-yyyy");


        Button send = this.sendButton(startDate, endDate);
        params.add(startDate, endDate, send);
        this.results.addClassName("fix-left-padding");
        add(params, this.results);

    }

    private Button sendButton(DatePickerWithPattern startDate, DatePickerWithPattern endDate) {

        Button send = new Button("Send",
                e -> {
                    ArrayList<Float> resultsData = null;
                    String from = this.dateChangeFormat(startDate.getValue().toString());
                    String to = this.dateChangeFormat(endDate.getValue().toString());

                    try {
                        Label sz = new Label("");
                        sz.setWidth(null);
                        sz.setHeight("30px");

                        resultsData = DataService.opcion1(this.datosCSV, from, to);
                        try {
                            this.results.removeAll();
                            this.results.add("Temperatura media: " + resultsData.get(0));
                            this.results.add(sz);
                            this.results.add("Velocidad media: " + resultsData.get(1));
                        } catch ( Exception ex)  {
                            Notification.show(ex.getMessage());
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        send.addClassName("vertical-positioning");

        return send;
    }

    private String dateChangeFormat(String date) {
        String[] splitted = date.split("-");
        Collections.reverse(Arrays.asList(splitted));
        return String.join("-", splitted);
    }




}
