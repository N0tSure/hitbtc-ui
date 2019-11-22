package com.artemsirosh.hitbtc.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created at 21-11-2019
 *
 * @author Artem Sirosh 'Artem.Sirosh@t-systems.com'
 */
@Slf4j
@Tag("div")
@Route(layout = MainLayout.class, value = "candles")
@UIScope
@SpringComponent
public class CandlesView extends Composite<VerticalLayout> implements HasUrlParameter<String> {

    private final Consumer<String> symbolParamProcessor;

    public CandlesView() {

        final Article noData = new Article();
        noData.add("Symbol is not be selected");
        noData.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        noData.getStyle().set("color", "var(--lumo-tertiary-text-color)");
        getContent().setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, noData);

        this.symbolParamProcessor = symbol -> {
            getContent().remove(noData);
            getContent().add(new H4("The symbol is "  + symbol));
        };

        getContent().setWidth("100%");
        getContent().add(new H2("Candles"), noData);

        log.info("CandlesView instantiated.");
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional.ofNullable(parameter)
            .ifPresent(symbolParamProcessor);
    }
}
