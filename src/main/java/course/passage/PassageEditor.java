package course.passage;

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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringComponent
@UIScope
public class PassageEditor extends VerticalLayout implements KeyNotifier {

    private final PassageRepository repository;

    public final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public final static Integer ID_LENGTH = 10000;

    private Passage passage;

    TextField statusId = new TextField("Код статуса");
    TextField schedule = new TextField("Время");
    TextField driverId = new TextField("Код водителя");
    TextField routeId = new TextField("Код маршрута");
    TextField busId = new TextField("Код автобуса");
    TextField dispatcherId = new TextField("Код диспетчера");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, delete);

    Binder<Passage> binder = new Binder<>(Passage.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PassageEditor(PassageRepository repository) {
        this.repository = repository;

        add(statusId, schedule, driverId, routeId, busId, dispatcherId, actions);

        binder.bind(statusId, p -> p.getStatusId() + "", (p, s) -> p.setStatusId(Integer.parseInt(s)));
        binder.bind(schedule, p -> p.getSchedule().format(FORMAT), (p, s) -> p.setSchedule(LocalTime.parse(s, FORMAT)));
        binder.bind(driverId, p -> p.getDriverId() + "", (p, s) -> p.setDriverId(Integer.parseInt(s)));
        binder.bind(routeId, p -> p.getRouteId() + "", (p, s) -> p.setRouteId(Integer.parseInt(s)));
        binder.bind(dispatcherId, p -> p.getDispatcherId() + "", (p, s) -> p.setDispatcherId(Integer.parseInt(s)));
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
        repository.delete(passage);
        changeHandler.onChange();
    }

    void save() {
        repository.save(passage);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPassage(Passage c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            passage = repository.findById(c.getId()).get();
        }
        else {
            c.setId((long)(Math.random() * ID_LENGTH));
            passage = c;
        }
        binder.setBean(passage);

        setVisible(true);

        statusId.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
