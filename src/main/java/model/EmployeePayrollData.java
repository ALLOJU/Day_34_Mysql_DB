package model;

import java.time.LocalDate;

public class EmployeePayrollData {

	private int id;
	public String name;
	public LocalDate start;
	public double basic_pay;
	/**
     * created a parameterized constructor
     * @param id id
     * @param name name
     * @param salary salary
     */
    public EmployeePayrollData(int id, String name, Double basic_pay) {
        setId(id);
        setName(name);
        setSalary(basic_pay);
    }

    /**
     * overloading  parameterized constructor
     * @param id id
     * @param name name
     * @param salary salary
     * @param startDate startDate
     */
    public EmployeePayrollData(int id, String name, Double salary,LocalDate start) {
        setId(id);
        setName(name);
        setSalary(salary);
        this.start = start;
    }

    //added getters and setters for id, name, salary
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return basic_pay;
    }

    public void setSalary(Double basic_pay) {
        this.basic_pay = basic_pay;
    }

    //Override toSting method
    @Override
    public String toString() {
        return "EmployeePayRollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basic_pay=" + basic_pay +
                '}'+'\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id &&
                     Double.compare(that.getSalary(), basic_pay) == 0 &&
                     name.equals(that.getName());
    }
}
