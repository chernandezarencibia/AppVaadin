package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("SecondView")
public class Second extends VerticalLayout {

    public Second(){
        Button button = new Button("Here!");
        add(button);
    }
}
