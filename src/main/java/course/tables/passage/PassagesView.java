package course.tables.passage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Route("passages")
public class PassagesView extends VerticalLayout {

    public final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final PassageRepository repo;

    final PassageEditor editor;

    final Grid<Passage> grid;

    TextField idFilter;
    TextField statusIdFilter;
    TextField scheduleFilter;
    TextField driverIdFilter;
    TextField routeIdFilter;
    TextField busIdFilter;
    TextField dispatcherIdFilter;

    final Button addNewBtn;

    public PassagesView(PassageRepository repo, PassageEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.idFilter = new TextField();
        this.statusIdFilter = new TextField();
        this.scheduleFilter = new TextField();
        this.driverIdFilter = new TextField();
        this.routeIdFilter = new TextField();
        this.busIdFilter = new TextField();
        this.dispatcherIdFilter = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                idFilter,
                statusIdFilter,
                scheduleFilter,
                driverIdFilter,
                routeIdFilter,
                busIdFilter,
                dispatcherIdFilter,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.setHeight("300px");

        grid.addColumn(Passage::getId).setHeader("Код");
        grid.addColumn(Passage::getStatusId).setHeader("Код статуса");
        grid.addColumn(Passage::getSchedule).setHeader("Время");
        grid.addColumn(Passage::getDriverId).setHeader("Код водителя");
        grid.addColumn(Passage::getRouteId).setHeader("Код маршрута");
        grid.addColumn(Passage::getBusId).setHeader("Код автобуса");
        grid.addColumn(Passage::getDispatcherId).setHeader("Код диспетчера");

        idFilter.setPlaceholder("Код");
        statusIdFilter.setPlaceholder("Код статуса");
        scheduleFilter.setPlaceholder("Время");
        driverIdFilter.setPlaceholder("Код водителя");
        routeIdFilter.setPlaceholder("Код маршрута");
        busIdFilter.setPlaceholder("Код автобуса");
        dispatcherIdFilter.setPlaceholder("Код диспетчера");

        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findById(Long.parseLong(filter)).stream().collect(Collectors.toList()));
        });
        statusIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        statusIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findByStatusId(Integer.parseInt(filter)));
        });
        scheduleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        scheduleFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findBySchedule(LocalTime.parse(filter, FORMAT)));
        });
        driverIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        driverIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findByDriverId(Integer.parseInt(filter)));
        });
        routeIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        routeIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findByRouteId(Integer.parseInt(filter)));
        });
        busIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        busIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findByBusId(Integer.parseInt(filter)));
        });
        dispatcherIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        dispatcherIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassages(filter, repo.findByDispatcherId(Integer.parseInt(filter)));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.editPassage(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editPassage(new Passage(null, LocalTime.MIN, 1, 529, "O031YB", 1, 1)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPassages(null, null);
        });

        listPassages(null, null);
    }

    void listPassages(String filterText, List<Passage> passages) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(passages);
        }
    }

}