package course.status;

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
public class StatusEditor extends VerticalLayout implements KeyNotifier {

    private final StatusRepository repository;

    private Status status;

    TextField name = new TextField("Название");
    TextField completeness = new TextField("Прогресс");
    TextField totalStops = new TextField("Всего остановок");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, delete);

    Binder<Status> binder = new Binder<>(Status.class);
    private ChangeHandler changeHandler;

    @Autowired
    public StatusEditor(StatusRepository repository) {
        this.repository = repository;

        add(name, completeness, totalStops, actions);

        binder.bind(completeness, p -> p.getCompleteness() + "", (p, v) -> p.setCompleteness(Double.parseDouble(v)));
        binder.bind(totalStops, p -> p.getTotalStops() + "", (p, v) -> p.setTotalStops(Integer.parseInt(v)));
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
        repository.delete(status);
        changeHandler.onChange();
    }

    void save() {
        repository.save(status);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editStatus(Status c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            status = repository.findById(c.getId()).get();
        }
        else {
            status = c;
        }

        binder.setBean(status);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
