package com.shelterApp.sheet;

import com.shelterApp.entity.Dog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import org.apache.http.HttpResponse;

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
        nameField.setEnabled(false);

        TextField lastName1Field = new TextField();
        lastName1Field.setLabel("First name");
        lastName1Field.setValue(employee.getString("lastName1"));
        lastName1Field.setEnabled(false);

        TextField lastName2Field = new TextField();
        lastName2Field.setLabel("Last name");
        lastName2Field.setValue(employee.getString("lastName2"));
        lastName2Field.setEnabled(false);

        TextField telephoneField = new TextField();
        telephoneField.setLabel("Telephone");
        telephoneField.setValue(String.valueOf(employee.getInt("telephone")));
        telephoneField.setEnabled(false);

        TextField emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setValue(employee.getString("email"));
        emailField.setEnabled(false);

        TextField dniField = new TextField();
        dniField.setLabel("DNI");
        dniField.setValue(employee.getString("dni"));
        dniField.setEnabled(false);

        fl.add(nameField, lastName1Field, lastName2Field,telephoneField,emailField,dniField);

        fl.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        add(fl);

        Label title = new Label("Dogs taken care by this employee");



        add(title);



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
                try{
                HttpPost post = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Dog/createDog");
                JSONObject jsonObjectDog = new JSONObject();
                jsonObjectDog.put("name", dog.getName());
                jsonObjectDog.put("breed", dog.getBreed());
                jsonObjectDog.put("age", String.valueOf(dog.getAge()));
                jsonObjectDog.put("code", String.valueOf(dog.getCode()));
                jsonObjectDog.put("img", " ");

                post.setEntity(new StringEntity(jsonObjectDog.toString()));
                post.setHeader("Content-type", "application/json");
                try (CloseableHttpClient httpClient = HttpClients.createDefault();
                     CloseableHttpResponse response = httpClient.execute(post)) {

                    System.out.println(EntityUtils.toString(response.getEntity()));
                }



            } catch(IOException e){
                e.printStackTrace();
            }
                return dog;
            }



            @Override
            public Dog update(Dog dog) {
                JSONObject dogJson = new JSONObject();
                dogJson.put("name",dog.getName());
                dogJson.put("breed", dog.getBreed());
                dogJson.put("age", dog.getAge());
                dogJson.put("code", dog.getCode());
                dogJson.put("img", " ");
                try{
                    String putEndpoint = "http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Dog/updateDog";
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpPut httpPut = new HttpPut(putEndpoint);
                    httpPut.setHeader("Accept", "application/json");
                    httpPut.setHeader("Content-type", "application/json");
                    StringEntity params =new StringEntity(dogJson.toString());
                    httpPut.setEntity(params);

                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(httpPut)) {

                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }

                } catch(IOException e){
                    e.printStackTrace();
                }

                return dog;
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
