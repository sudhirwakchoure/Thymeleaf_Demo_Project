package Controller;

import Entity.EmployeeEntity;
import Services.EmployeeService;
import Exception.RecordNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeMvcController {

    @Autowired
    EmployeeService service;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/list")
    public String getAllEmployees(Model model) {
        List<EmployeeEntity> list = service.getAllEmployees();
        model.addAttribute("employees", list);
        return "list-employees";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            EmployeeEntity entity = service.getEmployeeById(id.get());
            model.addAttribute("employee", entity);
        } else {
            model.addAttribute("employee", new EmployeeEntity());
        }
        return "add-edit-employee";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException {
        service.deleteEmployeeById(id);
        return "redirect:/list";
    }

    @RequestMapping(path = "/createEmployee", method = RequestMethod.POST)
    public String createOrUpdateEmployee(
            @Valid @ModelAttribute("employee") EmployeeEntity employee,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-edit-employee";
        }

        try {
            service.createOrUpdateEmployee(employee);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {

            bindingResult.rejectValue(
                    "email",                      // field name
                    "error.employee",
                    "Email already exists!"
            );

            return "add-edit-employee";
        }

        return "redirect:/list";
    }


}