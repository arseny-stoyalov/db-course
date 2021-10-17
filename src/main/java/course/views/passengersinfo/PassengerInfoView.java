package course.views.passengersinfo;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("passenger_info_report")
public class PassengerInfoView extends VerticalLayout {

    final Grid<PassengerInfo> grid;

    public PassengerInfoView(PassengerInfoRepository repo) {
        this.grid = new Grid<>();

        add(grid);

        grid.setHeight("300px");

        grid.addColumn(PassengerInfo::getName).setHeader("Имя");
        grid.addColumn(PassengerInfo::getSurname).setHeader("Фамилия");
        grid.addColumn(PassengerInfo::getPhone).setHeader("Телефон");
        grid.addColumn(PassengerInfo::getSeat).setHeader("Место");
        grid.addColumn(PassengerInfo::getPayed).setHeader("Место оплачено");
        grid.addColumn(PassengerInfo::getSchedule).setHeader("Расписание");
        grid.addColumn(PassengerInfo::getRouteNumber).setHeader("Номер маршрута");
        grid.addColumn(PassengerInfo::getRoute).setHeader("Маршрут").setAutoWidth(true);

        grid.setItems(repo.findAll());
    }

}