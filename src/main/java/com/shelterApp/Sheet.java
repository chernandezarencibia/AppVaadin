package com.shelterApp;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import java.util.List;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.navigator.ViewChangeListener;
import org.apache.poi.ss.usermodel.charts.LayoutMode;
import org.json.JSONArray;
import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

@Route("SecondView")
@UrlParameterMapping(":Id")
public class Sheet extends Div implements HasUrlParameterMapping {


    @UrlParameter
    public String Id;

    FormLayout fl = new FormLayout();
    public Sheet(){



        fl.setResponsiveSteps(new FormLayout.ResponsiveStep("10em", 1));
        Image img = new Image("http://localhost:8081/img/beagle.jpg", "algo");

        img.setHeight("10%");
        TextField labelField = new TextField();
        labelField.setLabel("Name");
        Button btn = new Button("Ver empleados");
        btn.addClickListener(e->{
           gottem();
        });
        TextField labelField2 = new TextField();
        labelField2.setLabel("Address");
        TextField labelField3 = new TextField();
        labelField3.setLabel("Name");
        fl.add(img, labelField, labelField2, labelField3, btn);
        add(fl);

    }


    public void gottem(){

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/getEmployeesByShelter?id="+Id);
        String s = target.request().get(String.class);
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println();
        }
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            employees.add(new Employee(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("lastName1"),
                    jsonArray.getJSONObject(i).getString("lastName2"), jsonArray.getJSONObject(i).getInt("telephone"),
                    jsonArray.getJSONObject(i).getString("email"), jsonArray.getJSONObject(i).getString("dni")));
        }


        Grid<Employee> employeeGrid = new Grid<>(Employee.class);
        employeeGrid.setItems(employees);
        employeeGrid.removeColumnByKey("id");

        employeeGrid.addSelectionListener(event -> {
//            Set<Employee> selected =  event.getAllSelectedItems();
//            Object[] personalData = selected.toArray();
//            getUI().ifPresent(ui -> ui.navigate("Sheet" + "/" + personalData.toString() + personalData[1]));
        });


        this.add(employeeGrid);
    }



}

