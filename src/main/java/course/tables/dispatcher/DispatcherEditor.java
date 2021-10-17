package course.tables.dispatcher;

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
public class DispatcherEditor extends VerticalLayout implements KeyNotifier {

    private final DispatcherRepository repository;

    private Dispatcher dispatcher;

    TextField name = new TextField("Имя");
    TextField surname = new TextField("Фамилия");
    TextField phone = new TextField("Номер телефона");
    TextField passageCount = new TextField("Количество рейсов");

    Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, delete);

    Binder<Dispatcher> binder = new Binder<>(Dispatcher.class);
    private ChangeHandler changeHandler;

    @Autowired
    public DispatcherEditor(DispatcherRepository repository) {
        this.repository = repository;

        add(name, surname, phone, passageCount, actions);

        binder.bind(passageCount, d -> d.getPassageCount() + "", (d, v) -> d.setPassageCount(Integer.parseInt(v)));
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
        repository.delete(dispatcher);
        changeHandler.onChange();
    }

    void save() {
        repository.save(dispatcher);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editDispatcher(Dispatcher c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            dispatcher = repository.findById(c.getId()).get();
        }
        else {
            dispatcher = c;
        }

        binder.setBean(dispatcher);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
