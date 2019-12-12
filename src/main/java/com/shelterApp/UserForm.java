package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;


@Route("UserForm")
public class UserForm extends VerticalLayout {

    TextField nameField;
    TextField lastName1Field;
    TextField lastName2Field;
    TextField telephoneField;
    TextField emailField;
    TextField dniField;
    public UserForm(){
        setForm();
    }

    private void setForm() {
        nameField = new TextField();
        nameField.setLabel("Name");
        nameField.setSizeFull();
        lastName1Field = new TextField();
        lastName1Field.setLabel("First Lastname");
        lastName1Field.setSizeFull();
        lastName2Field = new TextField();
        lastName2Field.setLabel("Second Lastname");
        lastName2Field.setSizeFull();
        telephoneField = new TextField();
        telephoneField.setLabel("Telephone");
        telephoneField.setSizeFull();
        emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setSizeFull();
        dniField = new TextField();
        dniField.setLabel("DNI");
        dniField.setSizeFull();

        Button createUser = new Button("Create user");
        Button cancelButton = new Button("Cancel Operation");

        createUser.addClickListener(e->{
           addUser();
        });

        cancelButton.addClickListener(e->{
           getUI().ifPresent(ui -> ui.navigate("MainView"));
        });

        add(nameField, lastName1Field, lastName2Field, telephoneField, emailField, dniField, createUser, cancelButton);
    }

    private void addUser() {
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/AppUser/createUser");
            JSONObject jsonObjectUser = new JSONObject();
            jsonObjectUser.put("name", nameField.getValue());
            jsonObjectUser.put("lastName1", lastName1Field.getValue());
            jsonObjectUser.put("lastName2", lastName2Field.getValue());
            jsonObjectUser.put("telephone", String.valueOf(telephoneField.getValue()));
            jsonObjectUser.put("email", emailField.getValue());
            jsonObjectUser.put("dni", dniField.getValue());
            post.setEntity(new StringEntity(jsonObjectUser.toString()));
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
