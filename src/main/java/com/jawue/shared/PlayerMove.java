package com.jawue.shared;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PlayerMove {
  private Character row;
  private Character  column;

  public Character getRow() {
    return row;
  }

  public void setRow(Character row) {
    this.row = row;
  }

  public Character getColumn() {
    return column;
  }

  public void setColumn(Character column) {
    this.column = column;
  }
}
