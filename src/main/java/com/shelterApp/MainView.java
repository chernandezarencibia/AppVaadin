package com.shelterApp;

import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.PWA;
import org.json.JSONArray;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "Eloy", shortName = "elo2")
public class MainView extends Div{
    FormLayout fl = new FormLayout();
    public MainView() {



            tryIt();



       Button routerButton = new Button("There!");
        routerButton.addClickListener( e-> {
            routerButton.getUI().ifPresent(ui -> ui.navigate("SecondView"));
        });

    }

    private void tryIt() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/ShelterApi-0.0.1-SNAPSHOT/rest/Shelter/getShelters");
        String s = target.request().get(String.class);

        JSONArray jsonArray = new JSONArray(s);

        List<Shelter> Shelters = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            Shelters.add(new Shelter(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("address"), jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("img")));

        }



//        Grid<Shelter> algo = new Grid<>(Shelter.class);
//        algo.setItems(Shelters);
//        algo.addColumn(Shelter::getName).setHeader("Name");
//        algo.addColumn(Shelter::getAddress).setHeader("Address");
//        algo.removeColumnByKey("id");
//        algo.removeColumnByKey("img");
//
//        this.add(algo);





        for (Shelter shelterOBJ: Shelters) {
            Image image = new Image(shelterOBJ.getImg(), "bg.png");
            image.setSizeFull();
            com.github.appreciated.card.Card card = new com.github.appreciated.card.Card(
                    // if you don't want the title to wrap you can set the whitespace = nowrap
                    new TitleLabel(shelterOBJ.getName()).withWhiteSpaceNoWrap(),
                    image,
                    new PrimaryLabel(shelterOBJ.getAddress()),
                    new SecondaryLabel("Some secondary text"),
                    new IconItem(new Icon(VaadinIcon.ABACUS), "Icon Item title", "Icon Item description"),
                    new Item("Item title", "Item description"),

                    new Actions(
                            new ActionButton("Sheet", event -> {getUI().ifPresent(ui -> ui.navigate("SecondView"+"/"+ String.valueOf(shelterOBJ.getId())));})
                    )

            );


            fl.add(card);
            add(fl);
        }



    }
            }
