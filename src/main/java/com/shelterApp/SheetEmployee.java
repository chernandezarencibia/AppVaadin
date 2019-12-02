package com.shelterApp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;

@Route("SheetEmployee")
@UrlParameterMapping(":id")
public class SheetEmployee extends Div implements HasUrlParameterMapping {
    FormLayout fl = new FormLayout();

    @UrlParameter
    public String id;

    public SheetEmployee(){
        Button btn = new Button("Try it");
        btn.addClickListener(e-> tryIt());
        fl.add(btn);
        add(fl);
    }

    private void tryIt() {
        System.out.println(id);
    }
}
