package com.codinghelmet.moreoojava.accountstates;

import com.codinghelmet.moreoojava.AccountState;
import com.codinghelmet.moreoojava.AccountUnfrozen;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * The Active state is the most engaged state for an Account :
 * We can deposit money unconditionally
 * We can withdraw money as long as the balance is higher than the withdrawn amount
 * We can close the account or freez it
 * We cannot holderVerified an account because it is already Verified (Active)
 */
public class Active implements AccountState {

  private AccountUnfrozen onUnfrozen;

  public Active(AccountUnfrozen onUnfrozen) {
    this.onUnfrozen = onUnfrozen;
  }

  /**
   * Deposit money
   * Since the state is Active, the action of addToBalance will be callback unconditionally
   *
   * @param amount : amount to deposit
   * @param addToBalance
   *
   * @return the Current state: Active
   */
  @Override
  public AccountState deposit(BigDecimal amount, Consumer<BigDecimal> addToBalance) {
    addToBalance.accept(amount);
    return this;
  }

  /**
   * Withdraw money
   * Since the state is Active, the action of subtractFromBalance will be callback under one condition :
   *
   * @param balance : should be higher than the amount to withdraw
   * @param amount to withdrawn
   * @param subtractFromBalance
   *
   * @return the Current state: Active
   */
  @Override
  public AccountState withdraw(BigDecimal balance, BigDecimal amount,
        Consumer<BigDecimal> subtractFromBalance) {
    if (balance.compareTo(amount) >= 0) {
      subtractFromBalance.accept(amount);
    }
    return this;
  }

  /**
   * Freeze the account
   * this method will change the state
   * from Active -To-> Frozen
   *
   * @return Frozen state
   */
  @Override
  public AccountState freezeAccount() {
    return new Frozen(this.onUnfrozen);
  }

  /**
   * Check the account as verified : make it Active
   * Since the Account is already at the Active state, it will return the current state
   *
   * @return the Current state: Active
   */
  @Override
  public AccountState holderVerified() {
    return this;
  }

  /**
   * Close the account
   * this method will change the state
   * from Active -To-> Closed
   *
   * @return Closed state
   */
  @Override
  public AccountState closeAccount() {
    return new Closed();
  }
}
