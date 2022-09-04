import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ReportsPageRoutingModule } from './reports-routing.module';

import { ReportsPageComponent } from './reports.page';

@NgModule({
  imports: [CommonModule, FormsModule, IonicModule, ReportsPageRoutingModule],
  declarations: [ReportsPageComponent],
})
export class ReportsPageModule {}
