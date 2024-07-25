import { Routes} from '@angular/router';
import { CreateEmployeeComponent } from "./create-employee/create-employee.component";
import { EmployeeDetailsComponent } from "./employee-details/employee-details.component";
import { EmployeeListComponent } from "./employee-list/employee-list.component";
import { UpdateEmployeeComponent } from "./update-employee/update-employee.component";
import { HomePageComponent } from './home-page/home-page.component';

export const routes: Routes = [

    {path:'home-page', component: HomePageComponent},
    {path: 'employees', component: EmployeeListComponent},
    {path: 'create-employee', component: CreateEmployeeComponent},
    {path: '', redirectTo: 'home-page', pathMatch: 'full'},
    {path: 'update-employee/:id', component: UpdateEmployeeComponent},
    {path: 'employee-details/:id', component: EmployeeDetailsComponent}
];
