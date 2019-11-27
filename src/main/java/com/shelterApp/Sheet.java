package com.shelterApp;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;

@Route("SecondView")
public class Second extends HorizontalLayout {

    public Second(){
        Button button = new Button("Here!");
        add(button);

    }



    }

