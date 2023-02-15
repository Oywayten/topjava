package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private static final String ID = "id";
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    ConfigurableApplicationContext appCtx;
    MealRestController mealController;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String parameter = request.getParameter(ID);
        meal.setId(parameter.isEmpty() ? null : getInt(request, ID));
        mealController.mealSetUserId(meal);
        log.info(meal.isNew() ? "doPost Create {}" : "Update {}", meal);
        if (parameter.isEmpty()) {
            mealController.create(meal);
        } else {
            mealController.update(meal, getInt(request, ID));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getInt(request, ID);
                log.info("Delete id={}", id);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action)
                        ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)
                        : mealController.get(getInt(request, ID));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("filter");
                request.setAttribute("startDate", getDate(request, "startDate"));
                request.setAttribute("endDate", getDate(request, "endDate"));
                request.setAttribute("startTime", getTime(request, "startTime"));
                request.setAttribute("endTime", getTime(request, "endTime"));
                request.setAttribute("meals", mealController.getAllToByDateTime(
                        getDate(request, "startDate"), getDate(request, "endDate"), getTime(request, "startTime"), getTime(request, "endTime")));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealController.getAllTo());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private static LocalDate getDate(HttpServletRequest request, String date) {
        String param = request.getParameter(date);
        return param.isEmpty() ? null : LocalDate.parse(param);
    }

    private static LocalTime getTime(HttpServletRequest request, String time) {
        String param = request.getParameter(time);
        return param.isEmpty() ? null : LocalTime.parse(param);
    }

    private static int getInt(HttpServletRequest request, String param) {
        String paramId = Objects.requireNonNull(request.getParameter(param));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
