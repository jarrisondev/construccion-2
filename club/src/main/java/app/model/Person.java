package app.model;

public class Person {
  // id long; cedula long; name:string; cellPhone: long

  private long id;
  private long cedula;
  private String name;
  private long cellPhone;

  public Person() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCedula() {
    return cedula;
  }

  public void setCedula(long cedula) {
    this.cedula = cedula;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(long cellPhone) {
    this.cellPhone = cellPhone;
  }

}
