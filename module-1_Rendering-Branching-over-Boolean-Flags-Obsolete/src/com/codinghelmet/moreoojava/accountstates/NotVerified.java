package com.codinghelmet.moreoojava.accountstates;

import com.codinghelmet.moreoojava.AccountState;
import com.codinghelmet.moreoojava.AccountUnfrozen;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * NotVerified State: it is the first state of an Account (at the moment of its creation)
 * At this state : we have less restriction than the Closed state:
 * We can deposit money
 * We can make the account verified -> Active state
 * Or we can close the account -> Closed state
 * But we cannot withdraw money, neither freeze the account at the NotVerified State (that's why the state doesn't change
 * at these two methods and remained NotVerified)
 */
public class NotVerified implements AccountState {

  private AccountUnfrozen onUnfrozen;

  public NotVerified(AccountUnfrozen onUnfrozen) {
    this.onUnfrozen = onUnfrozen;
  }

  /**
   * Deposit an amount of money
   *
   * @param amount ! amount to add to the balance
   * @param addToBalance callback to addToBalance
   *
   * @return the Current State : NotVerified
   */
  @Override
  public AccountState deposit(BigDecimal amount, Consumer<BigDecimal> addToBalance) {
    addToBalance.accept(amount);
    return this;
  }

  /**
   * Withdraw an amount of money from the Account
   * We cannot withdraw money at this NotVerified state: so the state remain NotVerified and no action occurs on the
   * balance
   *
   * @param balance : the current balance
   * @param amount : the amount to withdraw
   * @param subtractFromBalance : this method won't be called-back because of the NotVerified State
   *
   * @return the Current State : NotVerified
   */
  @Override
  public AccountState withdraw(BigDecimal balance, BigDecimal amount,
        Consumer<BigDecimal> subtractFromBalance) {
    return this;
  }

  /**
   * Freeze the account
   * in the NotVerified state we cannot freeze the account that's why we keep the same state "notVerified"
   *
   * @return the Current State : NotVerified
   */
  @Override
  public AccountState freezeAccount() {
    return this;
  }

  /**
   * Check the Account as Verified and make it Active
   * When this method is called, we change the state
   * from NotVerified -To-> Active
   *
   * @return the Active State
   */
  @Override
  public AccountState holderVerified() {
    return new Active(this.onUnfrozen);
  }

  /**
   * Close the account
   * When this method is called, we change the state
   * from NotVerified -To-> Closed
   *
   * @return the Closed state
   */
  @Override
  public AccountState closeAccount() {
    return new Closed();
  }
}
