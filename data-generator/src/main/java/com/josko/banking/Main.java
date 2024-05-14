package com.josko.banking;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	private static final int NUM_CUSTOMERS = 100;
	private static final int NUM_ACCOUNTS = 130;
	private static final int NUM_TRANSACTIONS = 100000;
	private static final String GENERATED_DIR = "./generated/";
	private static final String CUSTOMERS_FILE = GENERATED_DIR + "customers.csv";
	private static final String ACCOUNTS_FILE = GENERATED_DIR + "accounts.csv";
	private static final String TRANSACTIONS_FILE = GENERATED_DIR + "transactions.csv";

	private static final Random random = new Random();

	public static void main(String[] args) {
		generateCustomers();
		generateAccounts();
		generateTransactions();
	}


	private static void generateCustomers() {
		try (FileWriter writer = new FileWriter(CUSTOMERS_FILE)) {
			writer.write("customerId,name,address,email,phoneNumber\n");
			for (int i = 0; i < NUM_CUSTOMERS; i++) {
				String customerId = String.valueOf(i + 1);
				String name = "Customer " + customerId;
				String address = "Address " + customerId;
				String email = "customer" + customerId + "@example.com";
				String phoneNumber = generatePhoneNumber();
				writer.write(customerId + "," + name + "," + address + "," + email + "," + phoneNumber + "\n");
			}
			System.out.println("Customers CSV generated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateAccounts() {
		try (FileWriter writer = new FileWriter(ACCOUNTS_FILE)) {
			writer.write("accountId,iban,type,balance,pastMonthTurnover,customerId\n");
			for (int i = 0; i < NUM_ACCOUNTS; i++) {
				String accountId = String.valueOf(i + 1);
				String iban = generateIBAN();
				String type = generateAccountType();
				
				String customerId;
				if (i < NUM_CUSTOMERS) { // give each customer an account
					customerId = String.valueOf(i + 1);
				} else { // assign to a random customer after each customer has one account
					customerId = String.valueOf(random.nextInt(NUM_CUSTOMERS) + 1);
				}
				
				writer.write(accountId + "," + iban + "," + type + "," + customerId + "\n");
			}
			System.out.println("Accounts CSV generated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateTransactions() {
		try (FileWriter writer = new FileWriter(TRANSACTIONS_FILE)) {
			writer.write("transactionId,amount,message,timestamp,senderAccountId,receiverAccountId\n");
			for (int i = 0; i < NUM_TRANSACTIONS; i++) {
				String transactionId = String.valueOf(i + 1);
				double amount = roundToTwoDecimal((random.nextDouble() + 0.001) * 1000);
				String message = "Transaction " + transactionId;
				Instant timestamp = Instant.now().minusSeconds(random.nextInt(3600 * 24 * 365)); // Random timestamp within a year
				String senderAccountId = String.valueOf(random.nextInt(NUM_ACCOUNTS) + 1);
				
				String receiverAccountId;
				do {
					receiverAccountId = String.valueOf(random.nextInt(NUM_ACCOUNTS) + 1);
				} while (senderAccountId.equals(receiverAccountId));
				
				writer.write(transactionId + "," + amount + "," + message + "," + timestamp + "," + senderAccountId + "," + receiverAccountId + "\n");
			}
			System.out.println("Transactions CSV generated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String generatePhoneNumber() {
		StringBuilder phoneNumber = new StringBuilder("+");
		for (int i = 0; i < 10; i++) {
			phoneNumber.append(random.nextInt(10));
		}
		return phoneNumber.toString();
	}

	private static String generateIBAN() {
		StringBuilder iban = new StringBuilder("IBAN");
		for (int i = 0; i < 26; i++) {
			iban.append(random.nextInt(10));
		}
		return iban.toString();
	}

	private static String generateAccountType() {
		List<String> types = new ArrayList<>();
		types.add("SAVINGS");
		types.add("CHECKING");
		types.add("CREDIT");
		types.add("DEBIT");
		
		return types.get(random.nextInt(types.size()));
	}

	private static double roundToTwoDecimal(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
