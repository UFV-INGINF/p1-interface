package com.ufv.p1.front;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import com.ufv.p1.back.ColmenarViejoCSV;
import com.ufv.p1.back.DateParser;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

import static com.ufv.p1.back.DateParser.*;

import com.ufv.p1.back.*;

@Service
public class DataService implements Serializable {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }

    public ArrayList<ColmenarViejoCSV> getColmenarViejoCSV() {
        CSVManip read_csv = new CSVManip();
        ArrayList<ColmenarViejoCSV> registros_cv = read_csv.leeCSVEstaciones();
        return registros_cv;
    }

    public static ArrayList<Float> opcion1(ArrayList<ColmenarViejoCSV> registros_cv, String fecha_inicio, String fecha_fin) throws IOException {
        /*
         * https://www.w3schools.com/java/java_switch.asp
         * */

        ArrayList<Float> results = new ArrayList<>();
        Date fecha_inicio_date = DateParser.dateFormatter(fecha_inicio);
        Date fecha_fin_date = DateParser.dateFormatter(fecha_fin);

        if (fecha_inicio.compareTo(fecha_fin) > 0) {
            Notification.show("FECHA INICIAL NO PUEDE SER MENOR QUE FECHA FINAL.");
            return results;
            //Aquí habría que sacar un mensaje
        } else {
            ArrayList<Float> temp_medias = new ArrayList<>();
            ArrayList<Float> vel_viento = new ArrayList<>();
            for (ColmenarViejoCSV registro : registros_cv) {
                if (registro.getFecha().compareTo(fecha_inicio_date) >= 0 &&
                        registro.getFecha().compareTo(fecha_fin_date) <= 0) {
                    temp_medias.add(registro.getTmed());
                    vel_viento.add(registro.getVelmedia());
                }
            }
            float max_viento = 0;
            for (float i : vel_viento) {
                max_viento = Math.max(max_viento, i);
            }

            float temp_media = 0;
            for (float i : temp_medias) {
                temp_media += i;
            }
            temp_media = temp_media / temp_medias.size();

            results.add(temp_media);
            results.add(max_viento);


        }
        return results;
    }


    public static ArrayList<ColmenarViejoCSV> opcion2(ArrayList<ColmenarViejoCSV> registros_cv) {
        ArrayList<ColmenarViejoCSV> results = new ArrayList<>();
        for (ColmenarViejoCSV registro : registros_cv) {
            if (Objects.equals(registro.getHoratmin(), "Varias")) {
                results.add(registro);

            }
        }
        return results;
    }
}
