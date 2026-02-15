package Services;

import Entity.EmployeeEntity;
import Repository.EmployeeRepository;
import Exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<EmployeeEntity> getAllEmployees() {
        List<EmployeeEntity> ListEmp = employeeRepository.findAll();
        if (!ListEmp.isEmpty()) {
            return ListEmp;
        } else {
            return new ArrayList<EmployeeEntity>();
        }
    }

    public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeEntity> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            return emp.get();
        } else {
            throw new RecordNotFoundException("Employee not found with id: " + id);
        }
    }

    public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) {
        if (entity.getId() == null) {
            entity = employeeRepository.save(entity);
            return entity;
        } else {
            Optional<EmployeeEntity> employee = employeeRepository.findById(entity.getId());
            if (employee.isPresent()) {
                EmployeeEntity newEntity = employee.get();
                newEntity.setEmail(entity.getEmail());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());
                newEntity = employeeRepository.save(newEntity);
                return newEntity;
            } else {
                entity = employeeRepository.save(entity);
                return entity;
            }
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id: " + id);
        }
    }
}