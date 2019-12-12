package com.shelterApp;

import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.TitleLabel;


import com.shelterApp.entity.Shelter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.PWA;


import org.json.JSONArray;


import javax.ws.rs.client.*;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;


/**
 * The main view contains a button and a click listener.
 */
@Route("MainView")
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




    }


