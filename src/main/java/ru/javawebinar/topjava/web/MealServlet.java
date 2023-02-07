package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealStore;
import ru.javawebinar.topjava.repository.MealStoreMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String MEALS_JSP = "/meals.jsp";
    private static final String EDIT_JSP = "/edit.jsp";
    private static final String CREATE_JSP = "/create.jsp";
    public static final String FORWARD_TO = "{} forward to {}";
    private final MealStore store;

    public MealServlet() {
        super();
        store = MealStoreMemory.getInstance();
    }

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = MEALS_JSP;
        String operation = "without_changes";
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            store.delete(id);
            operation = "delete meal";
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = EDIT_JSP;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = store.findById(id);
            request.setAttribute("meal", meal);
            operation = "for edit meal";
        } else if ("create".equalsIgnoreCase(action)) {
            forward = CREATE_JSP;
            operation = "for create meal";
        }
        request.setAttribute("list", MealsUtil.filteredByStreams(store.findAll(), LocalTime.of(10, 0), LocalTime.of(22, 0), 2000));
        request.getRequestDispatcher(forward).forward(request, response);
        log.debug(FORWARD_TO, operation, forward);
//        response.sendRedirect("meals.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id1 = request.getParameter("id");
        String operation = "without_changes";
        if (request.getParameter("id").length() > 0) {
            store.update(Integer.parseInt(request.getParameter("id")), LocalDateTime.parse(request.getParameter("date")),
                    request.getParameter("desc"), Integer.parseInt(request.getParameter("cal")));
            operation = "after update meal";
        } else {
            store.add(LocalDateTime.parse(request.getParameter("date")), request.getParameter("desc"), Integer.parseInt(request.getParameter("cal")));
            operation = "after create meal";
        }
        request.setAttribute("list", MealsUtil.filteredByStreams(store.findAll(), LocalTime.of(10, 0), LocalTime.of(22, 0), 2000));
        request.getRequestDispatcher(MEALS_JSP).forward(request, response);
        log.debug(FORWARD_TO, operation, MEALS_JSP);
    }
}
