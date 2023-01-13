package com.ufv.p1.front;

import com.ufv.p1.back.ColmenarViejoCSV;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Option2 extends VerticalLayout {

    ArrayList<ColmenarViejoCSV> datosCSV;

    VerticalLayout results = new VerticalLayout();

    public Option2(@Autowired ArrayList<ColmenarViejoCSV> datosCSV) {

        this.datosCSV = datosCSV;
        Button send = this.sendButton();
        add(send, this.results);

        this.results.addClassName("fix-left-padding");
    }

    private Button sendButton() {

        Button send = new Button("Send",
                e -> {
                    Grid dataFiltered = null;
                    Label sz = new Label("");
                    sz.setWidth(null);
                    sz.setHeight("30px");
                    try {
                        try {
                            dataFiltered = this.resultsGrid();
                            this.results.removeAll();
                            this.results.add(dataFiltered);
                            this.results.add(sz);
                            this.results.add("Total de elementos filtrados: " + dataFiltered.getDataProvider().size(new Query<>()));
                        } catch (Exception ex) {
                            Notification.show(ex.getMessage());
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

        return send;
    }

    private Grid resultsGrid() {

        Grid<ColmenarViejoCSV> grid = new Grid<>(ColmenarViejoCSV.class, false);
        grid.addColumn(ColmenarViejoCSV::getIndicativo).setHeader("Indicativo");
        grid.addColumn(ColmenarViejoCSV::getFecha).setHeader("Fecha");
        grid.addColumn(ColmenarViejoCSV::getTmed).setHeader("Temperatura media");
        grid.addColumn(ColmenarViejoCSV::getHoratmax).setHeader("Hora Temp Max");
        grid.addColumn(ColmenarViejoCSV::getVelmedia).setHeader("Velocidad media");
        grid.addColumn(ColmenarViejoCSV::getRacha).setHeader("Racha");
        grid.addColumn(ColmenarViejoCSV::getSol).setHeader("Hora Sol");


        ArrayList<ColmenarViejoCSV> infoColmenarViejo = DataService.opcion2(this.datosCSV);
        grid.setItems(infoColmenarViejo);

        return grid;
    }
}
