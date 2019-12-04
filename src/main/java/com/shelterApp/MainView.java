package com.shelterApp;

import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;


import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.PWA;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;


import javax.ws.rs.client.*;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;


/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "Eloy", shortName = "elo2")
public class MainView extends Div {
    FormLayout fl = new FormLayout();

    public MainView() throws IOException {
        loadData();
    }

    private void loadData() throws IOException {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Shelter/getShelters");

        String s = target.request().get(String.class);

        JSONArray jsonArray = new JSONArray(s);
        client.close();
        createShelterArray(jsonArray);


    }


    private void createShelterArray(JSONArray jsonArray) throws IOException {
        List<Shelter> Shelters = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Shelters.add(new Shelter(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("address"), jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("img")));
        }
        addCards(Shelters);
    }

    private void addCards(List<Shelter> shelters) throws IOException {
        for (Shelter shelterOBJ : shelters) {
            Image image = new Image(shelterOBJ.getImg(), "bg.png");
            image.setSizeFull();
            com.github.appreciated.card.Card card = new com.github.appreciated.card.Card(
                    // if you don't want the title to wrap you can set the whitespace = nowrap
                    new TitleLabel(shelterOBJ.getName()).withWhiteSpaceNoWrap(),
                    image,
                    new PrimaryLabel(shelterOBJ.getAddress()),
                    new Actions(
                            new ActionButton("Details", event -> {
                                getUI().ifPresent(ui -> ui.navigate("Shelter" + "/" + String.valueOf(shelterOBJ.getId())));
                            })
                    )

            );
            fl.add(card);
            add(fl);
        }
    }


        public void createShelter() throws IOException {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Shelter/createShelter");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("address", "allí"));
            params.add(new BasicNameValuePair("name", "Aquí"));
            params.add(new BasicNameValuePair("img", "soy aquí"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost);
            client.close();

//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPut httpPut = new HttpPut("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Shelter/updateShelter");
//        httpPut.setHeader("Accept", "application/json");
//        httpPut.setHeader("Content-type", "application/json");
//        String inputJson = "{\n" +
//                "  \"id\": \"5\",\n" +
//                "  \"address\": \" Av. Andalucia, 147, 29740 Torre del Mar, Málaga\",\n" +
//                "  \"name\": \"Albergue Municipal de Animales\"\n" +
//                "  \"img\": \"23\"\n" +
//                "}";
//
//        StringEntity stringEntity = new StringEntity(inputJson);
//        httpPut.setEntity(stringEntity);
//        HttpResponse response = client.execute(httpPut);
//        client.close();
        }

    }


