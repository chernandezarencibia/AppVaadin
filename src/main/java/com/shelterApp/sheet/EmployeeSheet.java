package com.shelterApp.sheet;

import com.shelterApp.entity.Dog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


//import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

@Route("EmployeeSheet")
@UrlParameterMapping(":id")
public class EmployeeSheet extends Div implements HasUrlParameterMapping {
    FormLayout fl = new FormLayout();

    @UrlParameter
    public String id;
    Button btn = new Button("See Employeee");
    public EmployeeSheet(){


        btn.addClickListener(e-> getEmployee());
        add(btn);

    }

    private void getEmployee(){

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/getEmployeeById?id="+id);
        String s = target.request().get(String.class);
        JSONObject employee = new JSONObject(s);
        client.close();
        setDataInView(employee);
    }

    private void setDataInView(JSONObject employee) {
        TextField nameField = new TextField();
        nameField.setLabel("Name");
        nameField.setValue(employee.getString("name"));

        TextField lastName1Field = new TextField();
        lastName1Field.setLabel("First name");
        lastName1Field.setValue(employee.getString("lastName1"));

        TextField lastName2Field = new TextField();
        lastName2Field.setLabel("Last name");
        lastName2Field.setValue(employee.getString("lastName2"));

        TextField telephoneField = new TextField();
        telephoneField.setLabel("Telephone");
        telephoneField.setValue(String.valueOf(employee.getInt("telephone")));

        TextField emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setValue(employee.getString("email"));

        TextField dniField = new TextField();
        dniField.setLabel("DNI");
        dniField.setValue(employee.getString("dni"));

        fl.add(nameField, lastName1Field, lastName2Field,telephoneField,emailField,dniField);

        fl.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        add(fl);

        Label title = new Label("Dogs taken care by this employee");



        add(title);

        Grid<Dog> dogGrid = new Grid<>(Dog.class);
//        dogGrid.setItems(dogs);
//        dogGrid.removeColumnByKey("id");
//        dogGrid.removeColumnByKey("img");

        dogGrid.addSelectionListener(event -> {
            Set<Dog> selected = event.getAllSelectedItems();
            Iterator<Dog> algo =  selected.iterator();
            while(algo.hasNext()){
                getUI().ifPresent(ui -> ui.navigate("DogSheet" + "/" + algo.next().getId()));
            }

        });

        //add(dogGrid);

        btn.setVisible(false);

        GridCrud<Dog> a = new GridCrud(Dog.class);
        a.getCrudFormFactory().setDisabledProperties("id", "img");
        a.getGrid().removeColumnByKey("id");
        a.getGrid().removeColumnByKey("img");
        a.setCrudListener(new CrudListener<Dog>() {
            @Override
            public Collection<Dog> findAll() {
                JSONArray dogArray = employee.getJSONArray("dogs");
                List<Dog> dogs = new ArrayList<>();

                for (int i = 0; i < dogArray.length(); i++) {
                    dogs.add(new Dog(dogArray.getJSONObject(i).getInt("id"), dogArray.getJSONObject(i).getString("name"), dogArray.getJSONObject(i).getString("breed"),
                            dogArray.getJSONObject(i).getInt("age"), dogArray.getJSONObject(i).getInt("code"), dogArray.getJSONObject(i).getString("img")));
                }
                return dogs;
            }

            @Override
            public Dog add(Dog dog) {
                JSONObject json = new JSONObject();

                json.put("name", dog.getName());
                json.put ("breed", dog.getBreed());
                json.put("age", String.valueOf(dog.getAge()));
                json.put("code", String.valueOf(dog.getCode()));
                json.put("img", " ");

                CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                try {
                    HttpPost request = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Dog/createDog");
                    StringEntity params = new StringEntity(json.toString());
                    request.addHeader("content-type", "application/json");
                    request.setEntity(params);
                    httpClient.execute(request);
                } catch (Exception ex) {

                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                

                return dog;
            }


            @Override
            public Dog update(Dog dog) {
                return null;
            }

            @Override
            public void delete(Dog dog) {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpDelete httpDelete = new HttpDelete("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Dog/deleteDogById?id="+dog.getId());
                try {
                    HttpResponse response = (HttpResponse) httpClient.execute(httpDelete);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        add(a);
    }
}
