import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';

import { KalkulatorComponent } from './kalkulator.component';

import { kalkulatorRoute } from './kalkulator.route';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [MagazynGrzewczySharedModule, ReactiveFormsModule, RouterModule.forChild([kalkulatorRoute])],
  declarations: [KalkulatorComponent]
})
export class KalkulatorModule {}
