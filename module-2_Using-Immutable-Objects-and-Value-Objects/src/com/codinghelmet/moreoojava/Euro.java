package com.codinghelmet.moreoojava;

import java.math.BigDecimal;

public class Euro extends Money {

  private String iso2Country;

  public Euro(BigDecimal amount, Currency currency, String iso2Country) {
    super(amount, currency);
    this.iso2Country = iso2Country;
  }

  /**
   * Equals method:
   * In this case we changed "instanceOf" by the double checks: "obj != null" && "obj.getClass == this.getClass"
   * Because the bug case could occur since the Euro class is extending from the Money class (possible bug cas with
   * instanceOf: Money instanceOf Euro)
   *
   * @param other
   *
   * @return
   */
  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass() == this.getClass() && this.equals((Euro) other);
  }

  private boolean equals(Euro other) {
    return super.equals(other) && this.iso2Country.equals(other.iso2Country);
  }

  /**
   * HashCode method:
   * when overriding a hashCode method in a subclass:
   * - Always call the hashCode of the parent class: super.hashCode
   * - and add the flavor from the current subclass then, like here:
   * ... * 31 + this.iso2Country.hashCode
   *
   * @return
   */
  @Override
  public int hashCode() {
    return super.hashCode() * 31 + this.iso2Country.hashCode();
  }

  @Override
  public String toString() {
    return super.toString() + " (" + this.iso2Country + ")";
  }
}
