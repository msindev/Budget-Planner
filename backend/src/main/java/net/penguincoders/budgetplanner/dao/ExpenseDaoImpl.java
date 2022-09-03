package net.penguincoders.budgetplanner.dao;

import net.penguincoders.budgetplanner.dto.request.NewExpenseDto;
import net.penguincoders.budgetplanner.dto.response.MonthlyExpenseResponse;
import net.penguincoders.budgetplanner.model.Expense;
import net.penguincoders.budgetplanner.model.ExpenseCategory;
import net.penguincoders.budgetplanner.model.ExpenseDocument;
import net.penguincoders.budgetplanner.model.MonthlyExpenseObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparing;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    private static final String USERNAME = "username";
    private static final String YEAR = "year.";
    private static final String MONTH = ".month.";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String addExpense(String username, NewExpenseDto newExpenseDto) {
        Expense expense = new Expense(new ObjectId(), newExpenseDto.getName(), newExpenseDto.getCategory(),
                newExpenseDto.getAmount(), newExpenseDto.getDate());
        if (newExpenseDto.getDescription() != null) {
            expense.setDescription(newExpenseDto.getDescription());
        }
        Query query = new Query(Criteria.where(USERNAME).is(username));
        int year = expense.getDate().getYear();
        int month = expense.getDate().getMonthValue();
        String monthQuery = YEAR + year + MONTH + month;
        Update update = new Update();
        update.push(monthQuery + ".expenses", expense);
        update.inc(monthQuery + ".total", expense.getAmount());
        Query monthExistsQuery = new Query(Criteria.where(USERNAME).is(username)).addCriteria(Criteria.where(monthQuery).exists(true));
        ExpenseDocument expenseDocument = mongoTemplate.findOne(monthExistsQuery, ExpenseDocument.class);
        if (expenseDocument == null) {
            Map<String, Double> categoryTotal = new HashMap<>();
            for (ExpenseCategory category : ExpenseCategory.values()) {
                categoryTotal.put(category.toString().toLowerCase(), 0.0);
            }
            Update categoryAndBudgetUpdate = new Update().set(monthQuery + ".category_total", categoryTotal);
            categoryAndBudgetUpdate.set(monthQuery+".monthly_budget", 0.0);
            mongoTemplate.updateFirst(query, categoryAndBudgetUpdate, ExpenseDocument.class);

        }
        update.inc(monthQuery + ".category_total." + expense.getCategory(), expense.getAmount());
        mongoTemplate.updateFirst(query, update, ExpenseDocument.class);
        return "Expense Added Successfully";
    }

    @Override
    public MonthlyExpenseResponse getMonthlyExpense(String username, int year, int month) {
        MonthlyExpenseResponse monthlyExpenseResponse = new MonthlyExpenseResponse(new ArrayList<>(), 0.0, new HashMap<>());
        String monthQuery = YEAR + year + MONTH + month;
        Query monthExistsQuery = new Query(Criteria.where(USERNAME).is(username)).addCriteria(Criteria.where(monthQuery).exists(true));
        ExpenseDocument expenseDocument = mongoTemplate.findOne(monthExistsQuery, ExpenseDocument.class);
        if (expenseDocument != null) {
            MonthlyExpenseObject monthlyExpenseObject = expenseDocument.getYearlyExpenses().get(year).getExpenses().get(month);
            monthlyExpenseObject.getExpenses().sort(comparing(Expense::getDate).reversed());
            monthlyExpenseResponse = new MonthlyExpenseResponse(monthlyExpenseObject.getExpenses(),
                    monthlyExpenseObject.getTotal(), monthlyExpenseObject.getCategoryTotal());
            return monthlyExpenseResponse;
        }
        return monthlyExpenseResponse;
    }

    @Override
    public String setMonthlyBudget(String username, int year, int month, double amount) throws NullPointerException {
        Query query = new Query(Criteria.where(USERNAME).is(username));
        Update update = new Update();
        String updateQuery = YEAR + year + MONTH + month + ".monthly_budget";
        update.set(updateQuery, amount);
        mongoTemplate.updateFirst(query, update, ExpenseDocument.class);
        return "Monthly Budget Added Successfully";
    }

    @Override
    public double getMonthlyBudget(String username, int year, int month) throws NullPointerException {
        String monthQuery = YEAR + year + MONTH + month;
        Query monthExistsQuery = new Query(Criteria.where(USERNAME).is(username)).addCriteria(Criteria.where(monthQuery).exists(true));
        ExpenseDocument expenseDocument = mongoTemplate.findOne(monthExistsQuery, ExpenseDocument.class);
        if(expenseDocument != null) {
            return expenseDocument.getYearlyExpenses().get(year).getExpenses().get(month).getMonthlyBudget();
        }
        return 0;
    }


}
