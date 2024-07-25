import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee.service';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-employee-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.scss'] // Note the plural styleUrls
})
export class EmployeeDetailsComponent implements OnInit {

  id!: number;
  employee!: Employee;
  employees!: Employee[];

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.employee = new Employee();
    this.employeeService.getEmployeeById(this.id).subscribe(data => {
      this.employee = data;

      // Convert binary image data to base64 string
      if (this.employee.profilePhoto) {
        this.employee.profilePhoto = 'data:image/jpeg;base64,' + this.employee.profilePhoto;
      }
    });
  }

  private getEmployees() {
    this.employeeService.getEmployeesList().subscribe(data => {
      this.employees = data;
    });
  }

  confirmDelete(id: number) {
    if (confirm('Are you sure you want to delete this profile?')) {
      this.deleteEmployee(id);
    }
  }

  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id).subscribe(data => {
      console.log(data);
      this.snackBar.open('Profile successfully deleted', 'Close', {
        duration: 3000
      });

      // Delay navigation by 3 seconds to allow the snackbar to show
      setTimeout(() => {
        this.router.navigate(['/']);
      }, 3000);
    });
  }

  updateEmployee(id: number) {
    this.router.navigate(['update-employee', id]);
  }
/*
  downloadFile() {
    const element = document.getElementById('employee-details');
    if (element) {
      html2canvas(element).then((canvas: HTMLCanvasElement) => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgWidth = 210; // A4 width in mm
        const pageHeight = 295; // A4 height in mm
        const imgHeight = canvas.height * imgWidth / canvas.width;
        let heightLeft = imgHeight;

        let position = 0;

        pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        while (heightLeft >= 0) {
          position -= pageHeight;
          pdf.addPage();
          pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
          heightLeft -= pageHeight;
        }

        pdf.save('employee-details.pdf');
      });
    }
  }

  */
}