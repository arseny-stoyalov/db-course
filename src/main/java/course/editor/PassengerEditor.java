package course.editor;

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
import course.dto.Passenger;
import course.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class PassengerEditor extends VerticalLayout implements KeyNotifier {

    private final PassengerRepository repository;

    private Passenger passenger;

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Passenger> binder = new Binder<>(Passenger.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PassengerEditor(PassengerRepository repository) {
        this.repository = repository;

        add(name, surname, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editPassenger(passenger));
        setVisible(false);
    }

    void delete() {
        repository.delete(passenger);
        changeHandler.onChange();
    }

    void save() {
        repository.save(passenger);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPassenger(Passenger c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            passenger = repository.findById(c.getId()).get();
        }
        else {
            passenger = c;
        }
        cancel.setVisible(persisted);

        binder.setBean(passenger);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
