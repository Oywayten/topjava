package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public Meal get(int id) {
        log.info("{} get {}", authUserId(), id);
        return service.get(id, authUserId());
    }

    public List<MealTo> getAllTo() {
        log.info("getAll");
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllToByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getFilteredTos(service.getAllByDates(authUserId(), startDate == null ? LocalDate.MIN : startDate,
                        endDate == null ? LocalDate.MAX.minusDays(1) : endDate), authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime, endTime == null ? LocalTime.MAX : endTime);
    }

    public Meal create(Meal meal) {
        log.info("{} create {}", authUserId(), meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("{} delete {}", authUserId(), id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("{} update {}", authUserId(), meal);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}