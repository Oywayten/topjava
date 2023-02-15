package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(meal, 1);
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
        } else if (Objects.equals(meal.getUserId(), userId)) {
            result = repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return result;
    }

    @Override
    public boolean delete(int id, int userId) {
        return get(id).getUserId() == userId && repository.remove(id) != null;
    }

    private Meal get(int id) {
        return repository.getOrDefault(id, null);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = get(id);
        return result.getUserId() == userId ? result : null;
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
    public List<Meal> getAllByDate(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return getAllByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(
                meal.getDateTime().truncatedTo(ChronoUnit.DAYS), startDate, endDate.plusDays(1)));
    }
}

