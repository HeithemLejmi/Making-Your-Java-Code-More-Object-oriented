package com.codinghelmet.moreoojava.accountstates;

import com.codinghelmet.moreoojava.AccountState;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Closed State :
 * if the Account is closed, it will remain closed (that's why we always "return this" in each method here
 * We cannot: deposit, withdraw, freezeAccount, holderVerified, or closeAccount.
 */
public class Closed implements AccountState {

  /**
   * Deposit an amount of money
   * We cannot deposit money in the current state Closed
   * So no action will occurs on the balance
   *
   * @param amount ! amount to add to the balance
   * @param addToBalance callback to addToBalance
   *
   * @return the Current State : Closed
   */
  @Override
  public AccountState deposit(BigDecimal amount, Consumer<BigDecimal> addToBalance) {
    return this;
  }

  /**
   * Withdraw an amount of money from the Account
   * We cannot withdraw money at this Closed state: so the state remain Closed and no action occurs on the
   * balance
   *
   * @param balance : the current balance
   * @param amount : the amount to withdraw
   * @param subtractFromBalance : this method won't be called-back because of the Closed State
   *
   * @return the Current State : Closed
   */
  @Override
  public AccountState withdraw(BigDecimal balance, BigDecimal amount,
        Consumer<BigDecimal> subtractFromBalance) {
    return this;
  }

  /**
   * Freeze the account
   * in the Closed state we cannot freeze the account that's why we keep the same state "Closed"
   *
   * @return the Current State : Closed
   */
  @Override
  public AccountState freezeAccount() {
    return this;
  }

  /**
   * Check the Account as Verified and make it Active
   * However in this Closed State, we cannot verify the account
   *
   * @return the current State: Closed
   */
  @Override
  public AccountState holderVerified() {
    return this;
  }

  /**
   * Close the account
   * But the account is already Closed so the state remains the same
   *
   * @return the Current State : Closed
   */
  @Override
  public AccountState closeAccount() {
    return this;
  }
}
