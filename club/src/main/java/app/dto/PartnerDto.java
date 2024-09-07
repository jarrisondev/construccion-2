package app.dto;

import java.sql.Date;

public class PartnerDto {
  private long id;
  private UserDto userId;
  private double amount;
  private String type;
  private Date creationDate;

  public PartnerDto() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserDto getUserId() {
    return userId;
  }

  public void setUserId(UserDto userId) {
    this.userId = userId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

}
