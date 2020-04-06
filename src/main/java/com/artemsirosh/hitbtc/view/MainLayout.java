package com.artemsirosh.hitbtc.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * Created on 17 Nov, 2019.
 *
 * This is layout for all view on HitBTC UI. Contains navigations, header and footer.
 *
 * @author Artemis A. Sirosh
 */
@Slf4j
@UIScope
@SpringComponent
public class MainLayout extends Composite<VerticalLayout> implements RouterLayout {

    private final Consumer<HasElement> contentRegisterer;
    private final Consumer<HasElement> contentUnRegisterer;

    public MainLayout() {
        final HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setPadding(true);
        header.add(new H1("HitBTC UI"));

        final VerticalLayout navBar = new VerticalLayout();
        navBar.setWidth("200px");

        final Button mainViewButton = new Button("Symbols");
        mainViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        navBar.add(new Anchor(RouteConfiguration.forSessionScope().getUrl(SymbolsView.class), mainViewButton));

        final HorizontalLayout center = new HorizontalLayout();
        center.setWidth("100%");
        center.setFlexGrow(1, navBar);
        center.add(navBar);

        this.contentRegisterer = hasElement -> center.getElement().appendChild(hasElement.getElement());
        this.contentUnRegisterer = hasElement -> center.getElement().removeChild(hasElement.getElement());

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setWidth("100%");
        footer.setPadding(true);

        getContent().setSizeFull();
        getContent().setPadding(false);
        getContent().setSpacing(false);
        getContent().add(header, center, footer);
        getContent().expand(center);

    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.contentRegisterer.accept(content);
    }

    @Override
    public void removeRouterLayoutContent(HasElement oldContent) {
        this.contentUnRegisterer.accept(oldContent);
    }
}
