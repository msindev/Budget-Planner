import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ReportsPageComponent } from './reports.page';

const routes: Routes = [
  {
    path: '',
    component: ReportsPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReportsPageRoutingModule {}
