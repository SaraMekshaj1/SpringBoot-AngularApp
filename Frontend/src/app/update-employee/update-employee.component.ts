import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-update-employee',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './update-employee.component.html',
  styleUrl: './update-employee.component.scss'
})
export class UpdateEmployeeComponent  implements OnInit {

  id!: number;
  employee: Employee = new Employee();
  selectedFile: File | null = null;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.employeeService.getEmployeeById(this.id).subscribe(
      (data) => {
        this.employee = data;
      },
      (error) => console.log(error)
    );
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit(){
   const formData: FormData = new FormData();
    formData.append('employee', new Blob([JSON.stringify(this.employee)], { type: 'application/json' }));
    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }

    this.http.put(`http://localhost:8080/api/v1/employees/${this.id}`, formData).subscribe(
      (data) => {
        this.goTodetails(this.id);
      },
      (error) => console.log(error)
    );
  }

   goTodetails(id: number){
    this.router.navigate(['employee-details',id]);
  }
}