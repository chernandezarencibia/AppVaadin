package com.shelterApp.sheet;

import com.shelterApp.entity.Appoint;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import org.apache.http.client.methods.CloseableHttpResponse;
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

@Route("DogSheet")
@UrlParameterMapping(":id")
public class DogSheet extends Div implements HasUrlParameterMapping {

    @UrlParameter
    public String id;
    Button btn = new Button("See Dog");

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
        btn.setVisible(false);
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


        GridCrud<Appoint> a = new GridCrud(Appoint.class);


        a.getCrudFormFactory().setDisabledProperties("dogName", "dogId", "userName");
        a.setCrudListener(new CrudListener<Appoint>() {
            @Override
            public Collection<Appoint> findAll() {
                dog.getString("name");
                JSONArray appointsJsonArray = dog.getJSONArray("appoints");
                List<Appoint> appointList = new ArrayList<>();

                for (int i = 0; i < dog.getJSONArray("appoints").length(); i++) {
                    WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/AppUser/getUserById?id="+dog.getJSONArray("appoints").getJSONObject(i).getJSONObject("id").getInt("userID"));
                    String s = target.request().get(String.class);
                    JSONObject user = new JSONObject(s);
                    appointList.add(new Appoint(dog.getString("name"),dog.getInt("id"), user.getString("name"), user.getInt("id"), appointsJsonArray.getJSONObject(i).getString("date")));
                }


                return appointList;
            }

            @Override
            public Appoint add(Appoint appoint) {
                try{
                    HttpPost post = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Appoint/createAppoint");
                    JSONObject idJson = new JSONObject();
                    idJson.put("userID", appoint.getUserId());
                    idJson.put("dogID", id);
                    JSONObject jsonObjectAppoint = new JSONObject();
                    jsonObjectAppoint.put("id",idJson);
                    jsonObjectAppoint.put("date", appoint.getDate());


                    post.setEntity(new StringEntity(jsonObjectAppoint.toString()));
                    post.setHeader("Content-type", "application/json");

                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(post)) {
                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }

                } catch(IOException e){
                    e.printStackTrace();
                }
                return appoint;
            }

            @Override
            public Appoint update(Appoint appoint) {
                JSONObject appointJson = new JSONObject();
                JSONObject idJson = new JSONObject();
                idJson.put("userID", appoint.getUserId());
                idJson.put("dogID", id);
                appointJson.put("date", appoint.getDate());
                appointJson.put("id", id);
                try{
                    String putEndpoint = "http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Appoint/updateAppoint";


                    HttpPut httpPut = new HttpPut(putEndpoint);
                    httpPut.setHeader("Accept", "application/json");
                    httpPut.setHeader("Content-type", "application/json");


                    StringEntity params = new StringEntity(appointJson.toString());

                    httpPut.setEntity(params);

                    try (CloseableHttpClient httpClient = HttpClients.createDefault();
                         CloseableHttpResponse response = httpClient.execute(httpPut)) {

                        System.out.println(EntityUtils.toString(response.getEntity()));
                    }


                } catch(IOException e){
                    e.printStackTrace();
                }
                return appoint;
            }

            @Override
            public void delete(Appoint appoint) {

            }
        });


        fl.add(img, nameField, breedField, codeField, ageField,a);


        add(fl);
        btn.setVisible(false);




    }
}
