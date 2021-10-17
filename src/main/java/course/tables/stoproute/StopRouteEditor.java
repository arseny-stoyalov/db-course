package course.tables.stoproute;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class StopRouteEditor extends VerticalLayout implements KeyNotifier {

    private final StopRouteRepository repository;

    private StopRoute stopRoute;

    TextField routeId = new TextField("Код маршрута");
    TextField stopId = new TextField("Код остановки");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, delete);

    Binder<StopRoute> binder = new Binder<>(StopRoute.class);
    private ChangeHandler changeHandler;

    @Autowired
    public StopRouteEditor(StopRouteRepository repository) {
        this.repository = repository;

        add(routeId, stopId, actions);

        binder.bind(routeId, p -> p.getRouteId() + "", (p, v) -> p.setRouteId(Integer.parseInt(v)));
        binder.bind(stopId, p -> p.getStopId() + "", (p, v) -> p.setStopId(Integer.parseInt(v)));
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        setVisible(false);
    }

    void delete() {
        repository.delete(stopRoute);
        changeHandler.onChange();
    }

    void save() {
        repository.save(stopRoute);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editStopRoute(StopRoute c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            stopRoute = repository.findById(c.getId()).get();
        }
        else {
            stopRoute = c;
        }
        binder.setBean(stopRoute);

        setVisible(true);

        routeId.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
