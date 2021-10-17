package course.tables.passenger;

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

@Route("passengers")
public class PassengersView extends VerticalLayout {

    private final PassengerRepository repo;

    final PassengerEditor editor;

    final Grid<Passenger> grid;

    final TextField idFilter;
    final TextField nameFilter;
    final TextField surnameFilter;
    final TextField seatIdFilter;
    final TextField phoneFilter;
    final TextField payedFilter;

    final Button addNewBtn;

    public PassengersView(PassengerRepository repo, PassengerEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>();

        this.idFilter = new TextField();
        this.nameFilter = new TextField();
        this.surnameFilter = new TextField();
        this.seatIdFilter = new TextField();
        this.phoneFilter = new TextField();
        this.payedFilter = new TextField();

        this.addNewBtn = new Button("Новый", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(
                nameFilter,
                surnameFilter,
                seatIdFilter,
                phoneFilter,
                payedFilter,
                addNewBtn
        );
        add(actions, grid, editor);

        grid.addColumn(Passenger::getId).setHeader("Код");
        grid.addColumn(Passenger::getName).setHeader("Имя");
        grid.addColumn(Passenger::getSurname).setHeader("Фамилия");
        grid.addColumn(Passenger::getSeatId).setHeader("Код места");
        grid.addColumn(Passenger::getPhoneNumber).setHeader("Номер телефона");
        grid.addColumn(Passenger::getPayed).setHeader("Оплачено");

        grid.setHeight("300px");

        idFilter.setPlaceholder("По коду");
        nameFilter.setPlaceholder("По имени");
        surnameFilter.setPlaceholder("По фамилии");
        seatIdFilter.setPlaceholder("По коду места");
        phoneFilter.setPlaceholder("По номеру телефона");
        payedFilter.setPlaceholder("По статусу оплаты");

        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findById(Long.parseLong(filter)).stream().collect(Collectors.toList()));
        });
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findByNameStartsWithIgnoreCase(filter));
        });
        surnameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        surnameFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findBySurnameStartsWithIgnoreCase(filter));
        });
        seatIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        seatIdFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findBySeatId(Integer.parseInt(filter)));
        });
        phoneFilter.setValueChangeMode(ValueChangeMode.EAGER);
        phoneFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findByPhoneNumberStartsWithIgnoreCase(filter));
        });
        payedFilter.setValueChangeMode(ValueChangeMode.EAGER);
        payedFilter.addValueChangeListener(e -> {
            String filter = e.getValue();
            listPassengers(filter, repo.findByPayed(Boolean.parseBoolean(filter)));
        });

        grid.asSingleSelect().addValueChangeListener(e -> editor.editPassenger(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editPassenger(new Passenger(null, "", "", 1, "", false)));

        editor.setChangeHandler(() -> {
            String filter = "";
            editor.setVisible(false);
            listPassengers(filter, repo.findBySurnameStartsWithIgnoreCase(filter));
        });

        listPassengers(null, null);
    }

    void listPassengers(String filterText, List<Passenger> passengers) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(passengers);
        }
    }

}