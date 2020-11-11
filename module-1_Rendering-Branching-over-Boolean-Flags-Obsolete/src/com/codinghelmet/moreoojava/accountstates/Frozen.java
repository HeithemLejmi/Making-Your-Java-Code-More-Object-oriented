package com.codinghelmet.moreoojava.accountstates;

import com.codinghelmet.moreoojava.AccountState;
import com.codinghelmet.moreoojava.AccountUnfrozen;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * The Frozen state of an Account
 * At this state, we can deposit or withdraw money (which will callback accordingly the addToBalance and
 * subtractFromBalance) and also Unfreeze the Account (after the deposit or the withdraw, the frozen account becomes Active)
 * We can also unfreeze the Account (make it Active)
 * We can also close the Account
 * We cannot froze the Account (because it is already frozen)
 * We cannot check the account as Verified at this state (the Account remains frozen)
 */
public class Frozen implements AccountState {

  private AccountUnfrozen onUnfrozen;

  public Frozen(AccountUnfrozen onUnfrozen) {
    this.onUnfrozen = onUnfrozen;
  }

  /**
   * Deposit money
   * Since the state is Frozen, the action of addToBalance will be callback unconditionally
   * At the same time: this frozen account is activated
   * the state change from Frozen -To-> Active
   *
   * @param amount : amount to deposit
   * @param addToBalance
   *
   * @return Active State
   */
  @Override
  public AccountState deposit(BigDecimal amount, Consumer<BigDecimal> addToBalance) {
    addToBalance.accept(amount);
    return this.unfreeze();
  }

  /**
   * Withdraw money
   * Since the state is Frozen, the action of subtractFromBalance will be callback under one condition (the balance
   * should be higher than the amount to withdraw)
   * At the same time: this frozen account is activated
   * the state change from Frozen -To-> Active
   *
   * @param balance : should be higher than the amount to withdraw
   * @param amount to withdrawn
   * @param subtractFromBalance
   *
   * @return Active State
   */
  @Override
  public AccountState withdraw(BigDecimal balance, BigDecimal amount,
        Consumer<BigDecimal> subtractFromBalance) {
    if (balance.compareTo(amount) >= 0) {
      subtractFromBalance.accept(amount);
    }
    return this.unfreeze();
  }

  /**
   * Unfreeze the Account
   * This is a specific method related to the Frozen state
   * It allows us to unfrozen the Account: so the state chenges
   * from Frozen -to-> Active
   *
   * @return
   */
  private AccountState unfreeze() {
    this.onUnfrozen.handle();
    return new Active(this.onUnfrozen);
  }

  /**
   * Close the account
   * But the account is already Frozen so the state remains the same
   *
   * @return the Current State : Frozen
   */
  @Override
  public AccountState freezeAccount() {
    return this;
  }

  /**
   * Check the Account as Verified and make it Active
   * However in this Frozen State, we cannot verify the account
   *
   * @return the current State: Frozen
   */
  @Override
  public AccountState holderVerified() {
    return this;
  }

  /**
   * Close the account
   * in the Frozen state we can close the account
   * this method will change the state
   * from Frozen -To-> Closed
   *
   * @return Closed State
   */
  @Override
  public AccountState closeAccount() {
    return new Closed();
  }
}
