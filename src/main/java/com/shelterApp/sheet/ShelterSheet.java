package com.shelterApp.sheet;

import com.shelterApp.entity.Employee;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

@Route("Shelter")
@UrlParameterMapping(":Id")
public class ShelterSheet extends Div implements HasUrlParameterMapping {


    @UrlParameter
    public String Id;

    Button btn;
    TextField labelField;
    TextField labelField2;
    Image img;
    FormLayout fl = new FormLayout();
    Button getAll = new Button("See Shelter");
    Button btnEmployeeDetails = new Button("Details for employee");
    public ShelterSheet(){

        getAll.addClickListener(e-> getShelter());
        fl.setResponsiveSteps(new FormLayout.ResponsiveStep("10em", 1));

        add(getAll);
    }




    public void getEmployees(){
        btn.setVisible(false);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/getEmployeesByShelter?id="+Id);
        String s = target.request().get(String.class);

        client.close();


        GridCrud a = new GridCrud(Employee.class);
        a.getCrudFormFactory().setDisabledProperties("id");
        a.setCrudListener(new CrudListener<Employee>() {
            @Override
            public Collection<Employee> findAll() {
                JSONArray jsonArray = new JSONArray(s);
                List<Employee> employees = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    employees.add(new Employee(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("lastName1"),
                            jsonArray.getJSONObject(i).getString("lastName2"), jsonArray.getJSONObject(i).getInt("telephone"),
                            jsonArray.getJSONObject(i).getString("email"), jsonArray.getJSONObject(i).getString("dni")));
                }

                return employees;
            }

            @Override
            public Employee add(Employee employee) {
                try {

                    HttpPost post = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/createEmployee");

                    // add request parameter, form parameters
                    List<NameValuePair> urlParameters = new ArrayList<>();
                    urlParameters.add(new BasicNameValuePair("dni", employee.getDNI()));
                    urlParameters.add(new BasicNameValuePair("email", employee.getEmail()));
                    urlParameters.add(new BasicNameValuePair("lastName1", employee.getLastName1()));
                    urlParameters.add(new BasicNameValuePair("lastName2", employee.getLastName2()));
                    urlParameters.add(new BasicNameValuePair("shelterId", Id));
                    urlParameters.add(new BasicNameValuePair("telephone", String.valueOf(employee.getTelephone())));


                    post.setEntity(new UrlEncodedFormEntity(urlParameters));

                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(post)) {

                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }

                } catch(IOException e){
                    e.printStackTrace();
                }

                return employee;
            }

            @Override
            public Employee update(Employee employee) {

                JSONObject employeeJson = new JSONObject();
                employeeJson.put("id", employee.getId());
                employeeJson.put("name", employee.getName());
                employeeJson.put("lastName1", employee.getLastName1());
                employeeJson.put("lastName2", employee.getLastName2());
                employeeJson.put("telephone", employee.getTelephone());
                employeeJson.put("email", employee.getEmail());
                employeeJson.put("dni", employee.getDNI());
                try{
                    String putEndpoint = "http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/updateEmployee";


                    HttpPut httpPut = new HttpPut(putEndpoint);
                    httpPut.setHeader("Accept", "application/json");
                    httpPut.setHeader("Content-type", "application/json");


                    StringEntity params = new StringEntity(employeeJson.toString());

                    httpPut.setEntity(params);

                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(httpPut)) {

                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }


                } catch(IOException e){
                    e.printStackTrace();
                }

                return employee;
            }

            @Override
            public void delete(Employee employee) {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpDelete httpDelete = new HttpDelete("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Employee/deleteEmployeeById?id="+employee.getId());
                try {
                    HttpResponse response = httpClient.execute(httpDelete);
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


        this.add(a);
    }


    public void getShelter(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Shelter/getShelterById?id="+Id);
        String s = target.request().get(String.class);
        JSONObject shelter = new JSONObject(s);
        client.close();
        setDataInView(shelter);

    }

    public void setDataInView(JSONObject shelter){
        img = new Image(shelter.getString("img"), "algo");
        img.setHeight("10%");
        labelField = new TextField();
        labelField.setValue(shelter.getString("address"));
        labelField2 = new TextField();
        labelField2.setValue(shelter.getString("name"));
        labelField2.setLabel("Address");
        labelField.setLabel("Name");
        labelField.setEnabled(false);
        labelField2.setEnabled(false);

        btn = new Button("Employees");
        btn.addClickListener(e-> getEmployees());
        getAll.setVisible(false);


        fl.add(img, labelField, labelField2, btn);
        add(fl, btnEmployeeDetails);



        btnEmployeeDetails.addClickListener(e->{
            Dialog dialog = new Dialog();
            TextField employeeIdField = new TextField();
            Button confirmButton = new Button("Confirm");
            confirmButton.addClickListener(i->{
                getUI().ifPresent(ui -> ui.navigate("EmployeeSheet" + "/" +  employeeIdField.getValue()));
                dialog.close();
            });
            Button cancelButton = new Button("Cancel");
            cancelButton.addClickListener(a-> dialog.close());

            dialog.add(new Label("Insert the id of the employee"), employeeIdField);
            dialog.add(cancelButton, confirmButton);

            dialog.setWidth("250px");
            dialog.setHeight("150px");
            dialog.open();
        });

    }



}

