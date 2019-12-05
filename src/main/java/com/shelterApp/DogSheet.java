package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jdk.vm.ci.meta.Local;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route("DogSheet")
@UrlParameterMapping(":id")
public class DogSheet extends Div implements HasUrlParameterMapping {

    @UrlParameter
    public String id;
    Button btn = new Button("See Dog");
    Button btn1 = new Button("Add Appoint");
    Button btn2 = new Button("Update Appoint");
    Button btn3 = new Button("Delete appoint");
    Image img;
    TextField nameField;
    TextField breedField;
    TextField codeField;
    TextField ageField;
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

        nameField = new TextField();
        nameField.setLabel("Name");
        nameField.setValue(dog.getString("name"));

        breedField = new TextField();
        breedField.setLabel("Breed");
        breedField.setValue(dog.getString("breed"));

        codeField = new TextField();
        codeField.setLabel("Code");
        codeField.setValue(String.valueOf(dog.getInt("code")));

        ageField = new TextField();
        ageField.setLabel("Age (months)");
        ageField.setValue(String.valueOf(dog.getInt("age")));

        getAppoints(dog);



    }

    private void getAppoints(JSONObject dog) {
        Client client = ClientBuilder.newClient();
        Grid<Appoint> appointGrid = new Grid<>(Appoint.class);
        List<Appoint> appointList = new ArrayList<>();
        dog.getString("name");
        JSONArray appointsJsonArray = dog.getJSONArray("appoints");

        for (int i = 0; i < dog.getJSONArray("appoints").length(); i++) {
            WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/AppUser/getUserById?id="+dog.getJSONArray("appoints").getJSONObject(i).getJSONObject("id").getInt("userID"));
            String s = target.request().get(String.class);
            JSONObject user = new JSONObject(s);

           String date = appointsJsonArray.getJSONObject(i).getJSONObject("date").getInt("year") + "-"
                   + appointsJsonArray.getJSONObject(i).getJSONObject("date").getInt("monthValue") + "-"
                   + appointsJsonArray.getJSONObject(i).getJSONObject("date").getInt("dayOfMonth") + " "
                   + appointsJsonArray.getJSONObject(i).getJSONObject("date").getInt("hour") + ":"
                   + appointsJsonArray.getJSONObject(i).getJSONObject("date").getInt("minute");
            appointList.add(new Appoint(dog.getString("name"), user.getString("name"), date));
        }

        client.close();


        fl.add(img, nameField, breedField, codeField, ageField, appointGrid);
        appointGrid.setItems(appointList);
        appointGrid.removeColumnByKey("dogName");

        add(fl);
        btn.setVisible(false);




    }
}
