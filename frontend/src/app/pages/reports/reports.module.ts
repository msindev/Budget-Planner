import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { ReportsPageRoutingModule } from './reports-routing.module';

import { ReportsPageComponent } from './reports.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ReportsPageRoutingModule,
    NgxChartsModule,
  ],
  declarations: [ReportsPageComponent],
})
export class ReportsPageModule {}
