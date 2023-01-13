package com.ufv.p1.front;

import com.ufv.p1.back.ColmenarViejoCSV;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    VerticalLayout option1Container;
    VerticalLayout option2Container;
    VerticalLayout option3Container;
    VerticalLayout option4Container;


    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired DataService service) {

        ArrayList<ColmenarViejoCSV> datosCSV;
        datosCSV = service.getColmenarViejoCSV();

//        // Use TextField for standard text input
//        TextField textField = new TextField("Your name");
//        textField.addThemeName("bordered");
//
//        // Button click listeners can be defined as lambda expressions
//        Button button = new Button("Say hello",
//                e -> Notification.show(service.greet(textField.getValue())));
//
//        // Theme variants give you predefined extra styles for components.
//        // Example: Primary button has a more prominent look.
//        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        // You can specify keyboard shortcuts for buttons.
//        // Example: Pressing enter in this view clicks the Button.
//        button.addClickShortcut(Key.ENTER);
//
//        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
//        addClassName("centered-content");
//
//        add(textField, button);


        Option1 option1Generator = new Option1(datosCSV);
        option1Container = new VerticalLayout();
        option1Container.add(option1Generator);

        Option2 option2Generator = new Option2(datosCSV);
        option2Container = new VerticalLayout();
        option2Container.add(option2Generator);

        option3Container = new VerticalLayout();
        option4Container = new VerticalLayout();

        option2Container.setVisible(false);
        option3Container.setVisible(false);
        option4Container.setVisible(false);


        Tab option1 = new Tab("V.Max & Mean Temp");
        Tab option2 = new Tab("Time min temp & mean Temp");
        Tab option3 = new Tab("Save min temp 18-25");
        Tab option4 = new Tab("Station Location");

        Tabs tabs = new Tabs(option1, option2, option3, option4);
        tabs.addSelectedChangeListener(event -> {
                    this.hideContainers();
                    Tab selectedTab = event.getSelectedTab();
                    if (selectedTab == option1) {
                        option1Container.setVisible(true);
                    }
                    if (selectedTab == option2) {
                        option2Container.setVisible(true);
                    }
                    if (selectedTab == option3) {
                        option3Container.setVisible(true);
                    }
                    if (selectedTab == option4) {
                        option4Container.setVisible(true);
                    }
                }
        );
//                setContent(event.getSelectedTab())

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
//        setContent(tabs.getSelectedTab());

        add(tabs, content, option1Container, option2Container, option3Container, option4Container);


    }

    private void hideContainers() {
        option1Container.setVisible(false);
        option2Container.setVisible(false);
        option3Container.setVisible(false);
        option4Container.setVisible(false);
    }


}
