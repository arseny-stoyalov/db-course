package course.tables.status;

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

@Route("statuses")
public class StatusesView extends VerticalLayout {

    private final StatusRepository repo;

    final StatusEditor editor;

    final Grid<Status> grid;

    final TextField idFilter;
    final TextField nameFilter;
    final TextField completenessFilter;
    final TextField totalStops;

    final Button addNewBtn;

    public StatusesView(StatusRepository repo, StatusEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.idFilter = new TextField();
        this.nameFilter = new TextField();
        this.completenessFilter = new TextField();
        this.totalStops = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                nameFilter,
                completenessFilter,
                totalStops,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.setHeight("300px");

        grid.addColumn(Status::getId).setHeader("Код");
        grid.addColumn(Status::getName).setHeader("Название");
        grid.addColumn(Status::getCompleteness).setHeader("Прогресс");
        grid.addColumn(Status::getTotalStops).setHeader("Всего остановок");

        idFilter.setPlaceholder("Код");
        nameFilter.setPlaceholder("Название");
        completenessFilter.setPlaceholder("Прогресс");
        totalStops.setPlaceholder("Всего остановок");

        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStatuses(filter, repo.findById(Long.parseLong(filter)).stream().collect(Collectors.toList()));
        });
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStatuses(filter, repo.findByNameStartsWithIgnoreCase(filter));
        });
        completenessFilter.setValueChangeMode(ValueChangeMode.EAGER);
        completenessFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStatuses(filter, repo.findByCompleteness(Double.parseDouble(filter)));
        });
        totalStops.setValueChangeMode(ValueChangeMode.EAGER);
        totalStops.addValueChangeListener(e -> {
            String filter = e.getValue();
            listStatuses(filter, repo.findByTotalStops(Integer.parseInt(filter)));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.editStatus(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editStatus(new Status(null, "", 0., 1)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listStatuses(null, null);
        });

        listStatuses(null, null);
    }

    void listStatuses(String filterText, List<Status> statuses) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(statuses);
        }
    }

}