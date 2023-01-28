package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMeal = new ArrayList<>();
        Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();

        for(UserMeal meal : meals){
            if(!caloriesSumPerDay.containsKey(meal.getDateTime().toLocalDate())) {
                caloriesSumPerDay.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
            else caloriesSumPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        for(UserMeal meal : meals){
            UserMealWithExcess mealWithExcess = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                                                    meal.getCalories(),
                                                    caloriesSumPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);

            if(TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMeal.add(mealWithExcess);
            }
        }


        return userMeal;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumPerDay = meals.stream().collect(
                            Collectors.groupingBy(v -> v.getDateTime().toLocalDate(),
                                    Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> userMealWithExcessesList = meals.stream().filter(userMeal -> TimeUtil.isBetweenHalfOpen(
                        userMeal.getDateTime().toLocalTime(), startTime, endTime
        )).map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                userMeal.getCalories(), caloriesSumPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)).
                collect(Collectors.toList());


        return userMealWithExcessesList;
    }
}
