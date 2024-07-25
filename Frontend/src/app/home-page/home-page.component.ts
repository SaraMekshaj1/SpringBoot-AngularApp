import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RouterModule,FormsModule, CommonModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {
  employees!: Employee[];
  public searchQuery!: string;

  constructor(private employeeService: EmployeeService,
    private router: Router) { }
    
    search() {
      if (this.searchQuery) {
        this.employeeService.searchEmployees(this.searchQuery).subscribe(data => {
          this.employees = data;
        });
      }
    }
   
  
    private getEmployees(){
      this.employeeService.getEmployeesList().subscribe(data => {
        this.employees = data;
      });
    }

    employeeDetails(id: number){
      this.router.navigate(['employee-details', id]);
    }
  
    updateEmployee(id: number){
      this.router.navigate(['update-employee', id]);
    }
  
    deleteEmployee(id: number){
      this.employeeService.deleteEmployee(id).subscribe( data => {
        console.log(data);
        this.getEmployees();
      })
    }
   
}