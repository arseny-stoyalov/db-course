package course.views.busschedule;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("bus_schedule_report")
public class BusScheduleView extends VerticalLayout {

    final Grid<BusSchedule> grid;

    public BusScheduleView(BusScheduleRepository repo) {
        this.grid = new Grid<>();

        add(grid);

        grid.setHeight("300px");

        grid.addColumn(BusSchedule::getSchedule).setHeader("Расписание");
        grid.addColumn(BusSchedule::getRouteNumber).setHeader("Номер маршрута");
        grid.addColumn(BusSchedule::getRoute).setHeader("Маршрут");
        grid.addColumn(BusSchedule::getStatus).setHeader("Статус рейса");
        grid.addColumn(BusSchedule::getCompleteness).setHeader("Прогресс");

        grid.setItems(repo.findAll());
    }

}