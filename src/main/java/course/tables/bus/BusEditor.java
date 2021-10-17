package course.tables.bus;

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
public class BusEditor extends VerticalLayout implements KeyNotifier {

    private final BusRepository repository;

    private Bus bus;

    TextField registration = new TextField("Рег. номер");
    TextField repairNeeded = new TextField("Требуется ремонт");
    TextField stopId = new TextField("Код остановки");
    TextField parkingId = new TextField("Код стоянки");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, delete);

    Binder<Bus> binder = new Binder<>(Bus.class);
    private ChangeHandler changeHandler;

    @Autowired
    public BusEditor(BusRepository repository) {
        this.repository = repository;

        add(registration, repairNeeded, stopId, parkingId, actions);

        binder.bind(repairNeeded, b -> b.getRepairNeeded() + "", (b, s) -> b.setRepairNeeded(Boolean.parseBoolean(s)));
        binder.bind(stopId, b -> b.getStopId() + "", (b, s) -> b.setStopId(Integer.parseInt(s)));
        binder.bind(parkingId, b -> b.getParkingId() + "", (b, s) -> b.setParkingId(Integer.parseInt(s)));
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
        repository.delete(bus);
        changeHandler.onChange();
    }

    void save() {
        repository.save(bus);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void edit(Bus c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getRegistration() != null;
        if (persisted) {
            bus = repository.findById(c.getRegistration()).get();
        }
        else {
            bus = c;
        }

        binder.setBean(bus);

        setVisible(true);

        registration.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
