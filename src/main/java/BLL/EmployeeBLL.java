package BLL;

import DataAccess.EmployeeDAO;
import Model.Employee;

public class EmployeeBLL {
    private EmployeeDAO employeeDAO;

    public EmployeeBLL(){
        this.employeeDAO = new EmployeeDAO();
    }

    /**
     * @param username
     * @return
     */
    public Employee findEmployee(String username)
    {
        return employeeDAO.findByUsername(username);
    }
}
