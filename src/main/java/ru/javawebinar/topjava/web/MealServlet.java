package ru.javawebinar.topjava.web;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealStore;
import ru.javawebinar.topjava.repository.MemoryMealStore;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    public static final int CALORIES_PER_DAY = 2000;
    private static final long serialVersionUID = 1L;
    private static final String MEALS_JSP = "meals.jsp";
    private static final String MEALS = "meals";
    private static final String INSERT_OR_EDIT_MEAL_JSP = "/insertOrEditMeal.jsp";
    private static final String FORWARD_TO = "{} forward to {}";
    private static final String REDIRECT_TO = "{} redirect to {}";
    private static final Logger log = getLogger(MealServlet.class);
    private MealStore store;

    public MealServlet() {
    }

    @Override
    public void init() throws ServletException {
        store = new MemoryMealStore();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = "without_changes";
        String action = request.getParameter("action");
        String forward = MEALS_JSP;
        switch (Objects.isNull(action) ? MEALS : action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                store.delete(id);
                request.setAttribute("mealTOList", MealsUtil.getAllMealTo(store.findAll(), CALORIES_PER_DAY));
                response.sendRedirect(MEALS);
                operation = "delete meal";
                log.debug(REDIRECT_TO, operation, forward);
                break;
            case "edit":
                forward = INSERT_OR_EDIT_MEAL_JSP;
                id = Integer.parseInt(request.getParameter("id"));
                Meal meal = store.findById(id);
                request.setAttribute("meal", meal);
                operation = "for edit meal";
                break;
            case "create":
                forward = INSERT_OR_EDIT_MEAL_JSP;
                operation = "for create meal";
                break;
            default:
        }
        if (!"delete".equals(action)) {
            request.setAttribute("mealTOList", MealsUtil.getAllMealTo(store.findAll(), CALORIES_PER_DAY));
            request.getRequestDispatcher(forward).forward(request, response);
            log.debug(FORWARD_TO, operation, forward);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String operation;
        if (id.isEmpty()) {
            store.add(new Meal(LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"), parseCal(request)));
            operation = "after create meal";
        } else {
            store.update(new Meal(Integer.parseInt(id), LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("description"), parseCal(request)));
            operation = "after update meal";
        }
        request.setAttribute("mealTOList", MealsUtil.getAllMealTo(store.findAll(), CALORIES_PER_DAY));
        response.sendRedirect(MEALS);
        log.debug(REDIRECT_TO, operation, MEALS_JSP);
    }

    private static Integer parseCal(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("calorie"));
    }
}
