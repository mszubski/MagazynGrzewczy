import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';

import { KontaktComponent } from './kontakt.component';

import { kontaktRoute } from './kontakt.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild([kontaktRoute])],
  declarations: [KontaktComponent]
})
export class KontaktModule {}
