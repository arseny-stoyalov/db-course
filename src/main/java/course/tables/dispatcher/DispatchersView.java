package course.tables.dispatcher;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Route("dispatchers")
public class DispatchersView extends VerticalLayout {

    private final DispatcherRepository repo;

    final DispatcherEditor editor;

    final Grid<Dispatcher> grid;

    final TextField idFilter;
    final TextField nameFilter;
    final TextField surnameFilter;
    final TextField totalPassagesFilter;
    final TextField phoneFilter;

    final Button addNewBtn;

    public DispatchersView(DispatcherRepository repo, DispatcherEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.idFilter = new TextField();
        this.nameFilter = new TextField();
        this.surnameFilter = new TextField();
        this.totalPassagesFilter = new TextField();
        this.phoneFilter = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                idFilter,
                nameFilter,
                surnameFilter,
                totalPassagesFilter,
                phoneFilter,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.setHeight("300px");

        grid.addColumn(Dispatcher::getId).setHeader("Код");
        grid.addColumn(Dispatcher::getName).setHeader("Имя");
        grid.addColumn(Dispatcher::getSurname).setHeader("Фамилия");
        grid.addColumn(Dispatcher::getPassageCount).setHeader("Код рейса");
        grid.addColumn(Dispatcher::getPhone).setHeader("Номер телефона");

        idFilter.setPlaceholder("По коду");
        nameFilter.setPlaceholder("По имени");
        surnameFilter.setPlaceholder("По фамилии");
        totalPassagesFilter.setPlaceholder("По количеству рейсов");
        phoneFilter.setPlaceholder("По номеру телефона");

        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listDispatchers(filter, repo.findById(Long.parseLong(filter)).stream().collect(Collectors.toList()));
        });
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listDispatchers(filter, repo.findByNameStartsWithIgnoreCase(filter));
        });
        surnameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        surnameFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listDispatchers(filter, repo.findBySurnameStartsWithIgnoreCase(filter));
        });
        totalPassagesFilter.setValueChangeMode(ValueChangeMode.EAGER);
        totalPassagesFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listDispatchers(filter, repo.findByPassageCount(Integer.parseInt(filter)));
        });
        phoneFilter.setValueChangeMode(ValueChangeMode.EAGER);
        phoneFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listDispatchers(filter, repo.findByPhoneStartsWithIgnoreCase(filter));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.editDispatcher(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editDispatcher(new Dispatcher(null, "", "", "", 0)));

        editor.setChangeHandler(() -> {
            String filter = "";
            editor.setVisible(false);
            listDispatchers(filter, repo.findBySurnameStartsWithIgnoreCase(filter));
        });

        listDispatchers(null, null);
    }

    void listDispatchers(String filterText, List<Dispatcher> dispatchers) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(dispatchers);
        }
    }

}