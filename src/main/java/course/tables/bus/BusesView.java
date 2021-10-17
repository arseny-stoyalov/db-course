package course.tables.bus;

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

@Route("buses")
public class BusesView extends VerticalLayout {

    private final BusRepository repo;

    final BusEditor editor;

    final Grid<Bus> grid;

    final TextField registrationFilter;
    final TextField repairFilter;
    final TextField stopFilter;
    final TextField parkingFilter;

    final Button addNewBtn;

    public BusesView(BusRepository repo, BusEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.registrationFilter = new TextField();
        this.repairFilter = new TextField();
        this.stopFilter = new TextField();
        this.parkingFilter = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                registrationFilter,
                repairFilter,
                stopFilter,
                parkingFilter,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.setHeight("300px");

        grid.addColumn(Bus::getRegistration).setHeader("Рег. номер");
        grid.addColumn(Bus::getRepairNeeded).setHeader("Требуется ремонт");
        grid.addColumn(Bus::getStopId).setHeader("Код остановки");
        grid.addColumn(Bus::getParkingId).setHeader("Код стоянки");

        registrationFilter.setPlaceholder("По рег. номеру");
        repairFilter.setPlaceholder("Требуется ли ремонт");
        stopFilter.setPlaceholder("По коду остановки");
        parkingFilter.setPlaceholder("По коду стоянки");

        registrationFilter.setValueChangeMode(ValueChangeMode.EAGER);
        registrationFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listBuses(filter, repo.findById(filter).stream().collect(Collectors.toList()));
        });
        repairFilter.setValueChangeMode(ValueChangeMode.EAGER);
        repairFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listBuses(filter, repo.findByRepairNeeded(Boolean.parseBoolean(filter)));
        });
        stopFilter.setValueChangeMode(ValueChangeMode.EAGER);
        stopFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listBuses(filter, repo.findByStopId(Integer.parseInt(filter)));
        });
        parkingFilter.setValueChangeMode(ValueChangeMode.EAGER);
        parkingFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listBuses(filter, repo.findByParkingId(Integer.parseInt(filter)));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.edit(e.getValue()));

        addNewBtn.addClickListener(e -> editor.edit(new Bus(null, false, 1, 1)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listBuses(null, null);
        });

        listBuses(null, null);
    }

    void listBuses(String filterText, List<Bus> buses) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(buses);
        }
    }

}