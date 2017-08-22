package pe.dospe.spring.jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
/**
 * Handles requests for the Employee JDBC Service
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.dospe.spring.jdbc.model.Employee;

@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	@RequestMapping(value = "/rest/emps", method=RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees(){
		logger.info("Start getAllEmployees");
		List<Employee> employeeList = new ArrayList<Employee>();
		//JDBC Code Start
		String query = "select * from employee";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> employeeRows = jdbcTemplate.queryForList(query);
		for (Map<String, Object> empRow : employeeRows) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(String.valueOf(empRow.get("id"))));
			employee.setName(String.valueOf(empRow.get("name")));
			employee.setRole(String.valueOf(empRow.get("role")));
			employeeList.add(employee);
		}
		return employeeList;
	}
	
}
