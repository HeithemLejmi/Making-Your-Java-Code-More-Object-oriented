package com.codinghelmet.moreoojava;

import java.math.BigDecimal;

/**
 * Declared as final :
 * to prevent this class from having subclass
 * therefore to ensure that its behavior and internal state won't be override
 * ===> To keep this Value Object Immutable
 */
public final class Currency implements Comparable<Currency> {

  private String symbol;

  /**
   * Equals method
   * Here we left the "instanceOf" use, because we are sure that we wont stumble int the Bug case of:
   * ParentClass instanceOf DerivedClass, since this class doesn't extend from any other class and also it can't have any
   * subclass because it is declared as "final"
   *
   * @param other
   *
   * @return
   */
  @Override
  public boolean equals(Object other) {
    return other instanceof Currency && this.equals((Currency) other);
  }

  private boolean equals(Currency other) {
    return this.symbol.equals(other.symbol);
  }

  @Override
  public int hashCode() {
    return this.symbol.hashCode();
  }

  public Currency(String symbol) {
    this.symbol = symbol;
  }

  public Money zero() {
    return new Money(BigDecimal.ZERO, this);
  }

  @Override
  public int compareTo(Currency other) {
    return this.symbol.compareTo(other.symbol);
  }

  @Override
  public String toString() {
    return this.symbol;
  }
}
