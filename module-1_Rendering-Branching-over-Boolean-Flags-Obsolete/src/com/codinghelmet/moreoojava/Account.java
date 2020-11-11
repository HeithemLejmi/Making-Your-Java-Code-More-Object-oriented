package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.accountstates.Active;

import java.math.BigDecimal;

public class Account {

  private BigDecimal balance;
  private AccountState state;

  public Account(AccountUnfrozen onUnfrozen) {
    this.balance = BigDecimal.ZERO;
    this.state = new Active(onUnfrozen);
  }

  public void holderVerified() {
    this.state = this.state.holderVerified();
  }

  public void closeAccount() {
    this.state = this.state.closeAccount();
  }

  public void freezeAccount() {
    this.state = this.state.freezeAccount();
  }

  /**
   * Deposit an amount of money
   * consequences: the balance should be incremented (call the addToBalance())
   * and if the account was preveiosly frozen, it should be unfrozen
   *
   * @param amount
   */
  public void deposit(BigDecimal amount) {
    this.state = this.state.deposit(amount, this::addToBalance);
  }

  private void addToBalance(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  /**
   * Withdraw an amount of money
   * if the account is verified and not closed
   * consequences: the balances should decrease (call the substractFromBalance())
   * and if the account was preveiosly frozen, it should be unfrozen
   *
   * @param amount
   */
  public void withdraw(BigDecimal amount) {
    this.state = this.state.withdraw(
          this.balance, amount, this::subtractFromBalance);
  }

  private void subtractFromBalance(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
  }
}





































