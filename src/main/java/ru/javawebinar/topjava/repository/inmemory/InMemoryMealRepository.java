package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        List<Meal> meals = MealsUtil.meals;
        for (int i = 0; i < meals.size(); i++) {
            if (i < meals.size() / 2) {
                save(meals.get(i), 1);
            } else {
                save(meals.get(i), 2);
            }
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Meal result = null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            result = meal;
        } else if (get(meal.getId(), userId) != null) {
            meal.setUserId(userId);
            result = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return result;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean isDelete = false;
        if (checkMealAndSameUserId(id, userId) != null) {
            repository.remove(id);
            isDelete = true;
        }
        return isDelete;
    }

    private Meal checkMealAndSameUserId(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId ? meal : null;
    }

    @Override
    public Meal get(int id, int userId) {
        return checkMealAndSameUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllByPredicate(userId, meal -> true);
    }

    private List<Meal> getAllByPredicate(int userId, Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(filter)
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(
                meal.getDate(), startDate, endDate.plusDays(1)));
    }
}

