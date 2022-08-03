package net.penguincoders.budgetplanner.repository;

import net.penguincoders.budgetplanner.model.ExpenseDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<ExpenseDocument, ObjectId> {
    ExpenseDocument findByUsername(String username);
}
