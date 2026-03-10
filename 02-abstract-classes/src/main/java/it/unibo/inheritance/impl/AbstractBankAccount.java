package it.unibo.inheritance.impl;

import it.unibo.inheritance.api.AccountHolder;
import it.unibo.inheritance.api.BankAccount;

public abstract class AbstractBankAccount implements BankAccount {

    private static final double ATM_TRANSACTION_FEE = 1;

    private final AccountHolder holder;
    private double balance;
    private int transactions;

    public AbstractBankAccount(final AccountHolder accountHolder, final double balance) {
        this.holder = accountHolder;
        this.balance = balance;
        this.transactions = 0;
    }

    @Override
    public void chargeManagementFees(final int id) {
        final double fee = this.computeFee();
        if (this.isUserValid(id) && this.isWithDrawAllowed(fee)) {
            this.balance -= fee;
            this.transactions = 0;
        }
    }

    @Override
    public void deposit(final int id, final double amount) {
        this.applyTransaction(id, amount);
    }

    @Override
    public void depositFromATM(final int id, final double amount) {
        this.deposit(id, amount - ATM_TRANSACTION_FEE);
    }

    @Override
    public AccountHolder getAccountHolder() {
        return this.holder;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public int getTransactionsCount() {
        return this.transactions;
    }

    @Override
    public void withdraw(final int id, final double amount) {
        if (this.isWithDrawAllowed(amount)) {
            this.applyTransaction(id, -amount);
        }
    }

    @Override
    public void withdrawFromATM(final int id, final double amount) {
        this.withdraw(id, amount + ATM_TRANSACTION_FEE);
    }

    protected abstract boolean isWithDrawAllowed(double amount);

    protected abstract double computeFee();

    protected final boolean isUserValid(final int id) {
        return this.holder.getUserID() == id;
    }

    private void applyTransaction(final int id, final double amount) {
        if (this.isUserValid(id)) {
            this.balance += amount;
            this.transactions++;
        }
    }
}
