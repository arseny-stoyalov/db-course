package course.stoproute;

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

@Route("stoproutes")
public class StopRoutesView extends VerticalLayout {

    private final StopRouteRepository repo;

    final StopRouteEditor editor;

    final Grid<StopRoute> grid;

    final TextField idFilter;
    final TextField routeIdFilter;
    final TextField stopIdFilter;

    final Button addNewBtn;

    public StopRoutesView(StopRouteRepository repo, StopRouteEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.idFilter = new TextField();
        this.routeIdFilter = new TextField();
        this.stopIdFilter = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                idFilter,
                routeIdFilter,
                stopIdFilter,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.setHeight("300px");

        grid.addColumn(StopRoute::getId).setHeader("Код");
        grid.addColumn(StopRoute::getRouteId).setHeader("Код маршрута");
        grid.addColumn(StopRoute::getStopId).setHeader("Код остановки");

        idFilter.setPlaceholder("Код");
        routeIdFilter.setPlaceholder("Код маршрута");
        stopIdFilter.setPlaceholder("Код остановки");

        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStopRoutes(filter, repo.findById(Long.parseLong(filter)).stream().collect(Collectors.toList()));
        });
        routeIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        routeIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStopRoutes(filter, repo.findByRouteId(Integer.parseInt(filter)));
        });
        stopIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        stopIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStopRoutes(filter, repo.findByStopId(Integer.parseInt(filter)));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.editStopRoute(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editStopRoute(new StopRoute(null, 1, 1)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listStopRoutes(null, null);
        });

        listStopRoutes(null, null);
    }

    void listStopRoutes(String filterText, List<StopRoute> stopRoutes) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(stopRoutes);
        }
    }

}