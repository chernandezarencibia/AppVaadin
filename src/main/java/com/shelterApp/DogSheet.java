package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Route("DogSheet")
@UrlParameterMapping(":id")
public class DogSheet extends Div implements HasUrlParameterMapping {

    @UrlParameter
    public String id;
    Button btn = new Button("See Dog");
    Image img;
    FormLayout fl = new FormLayout();

    public DogSheet(){
        fl.setResponsiveSteps(new FormLayout.ResponsiveStep("10em", 1));
        btn.addClickListener(e-> getDog());
        add(btn);
    }

    private void getDog() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Dog/getDogById?id="+id);
        String s = target.request().get(String.class);
        JSONObject dog = new JSONObject(s);
        client.close();
        setDataInView(dog);
    }

    private void setDataInView(JSONObject dog) {
        img = new Image(dog.getString("img"), "bg.png");
        img.setHeight("10%");

        TextField nameField = new TextField();
        nameField.setLabel("Name");
        nameField.setValue(dog.getString("name"));

        TextField breedField = new TextField();
        breedField.setLabel("Breed");
        breedField.setValue(dog.getString("breed"));

        TextField codeField = new TextField();
        codeField.setLabel("Code");
        codeField.setValue(String.valueOf(dog.getInt("code")));

        TextField ageField = new TextField();
        ageField.setLabel("Age (months)");
        ageField.setValue(String.valueOf(dog.getInt("age")));

        fl.add(img, nameField, breedField, codeField, ageField);
        
        add(fl);

    }
}
