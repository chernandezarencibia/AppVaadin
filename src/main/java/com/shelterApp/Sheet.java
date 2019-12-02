package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

import java.util.Iterator;
import java.util.List;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONArray;
import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.Set;

@Route("SecondView")
@UrlParameterMapping(":Id")
public class Sheet extends Div implements HasUrlParameterMapping {


    @UrlParameter
    public String Id;

    FormLayout fl = new FormLayout();
    public Sheet(){

        getShelter();

        fl.setResponsiveSteps(new FormLayout.ResponsiveStep("10em", 1));
        Image img = new Image("http://localhost:8081/img/beagle.jpg", "algo");

        img.setHeight("10%");
        TextField labelField = new TextField();
        labelField.setLabel("Name");
        Button btn = new Button("Employees");
        btn.addClickListener(e->{
           getEmployees();
        });
        TextField labelField2 = new TextField();
        labelField2.setLabel("Address");
        fl.add(img, labelField, labelField2, btn);
        add(fl);

    }


    public void getEmployees(){

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
            Set<Employee> selected = event.getAllSelectedItems();
            Iterator<Employee> algo =  selected.iterator();
            while(algo.hasNext()){
                getUI().ifPresent(ui -> ui.navigate("SheetEmployee" + "/" + algo.next().getId()));
            }


        });


        this.add(employeeGrid);
    }

    public Shelter getShelter(){


        return null;
    }



}

