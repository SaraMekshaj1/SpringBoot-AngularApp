import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-employee',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './create-employee.component.html',
  styleUrl: './create-employee.component.scss'
})
export class CreateEmployeeComponent implements OnInit {

  employee: Employee = new Employee();
  formErrors: any = {}; // Object to store validation errors
  selectedFile: File | null = null;

  constructor(private employeeService: EmployeeService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  saveEmployee() {
    const formData = new FormData();
    formData.append('employee', new Blob([JSON.stringify(this.employee)], {
      type: 'application/json'
    }));
    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }

    this.employeeService.createEmployee(formData).subscribe(
      data => {
        console.log('Employee created successfully', data);
        this.goToEmployeeList();
      },
      error => {
        console.error('Error creating employee', error);
        if (error.status === 400) {
          this.formErrors = error.error; // Capture validation errors
        }
      }
    );
  }

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }

  onSubmit() {
    console.log(this.employee);
    this.saveEmployee();
  }
}
