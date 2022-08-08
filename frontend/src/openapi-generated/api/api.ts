export * from './authController.service';
import { AuthControllerService } from './authController.service';
export * from './expenseController.service';
import { ExpenseControllerService } from './expenseController.service';
export const APIS = [AuthControllerService, ExpenseControllerService];
