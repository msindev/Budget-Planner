import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { AddPageComponent } from './add.page';

import { AddPageRoutingModule } from './add-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    ReactiveFormsModule,
    AddPageRoutingModule,
  ],
  providers: [DatePipe],
  declarations: [AddPageComponent],
})
export class AddPageModule {}
