import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

import { NgChartsModule } from 'ng2-charts';
import { HomePageRoutingModule } from './home-routing.module';
import { HomePageComponent } from './home.page';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    HomePageRoutingModule,
    NgChartsModule,
  ],
  declarations: [HomePageComponent],
})
export class HomePageModule {}
