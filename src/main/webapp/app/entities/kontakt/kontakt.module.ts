import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';
import { AgmCoreModule } from '@agm/core';

import { KontaktComponent } from './kontakt.component';

import { kontaktRoute } from './kontakt.route';

@NgModule({
  imports: [
    MagazynGrzewczySharedModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDn6LWAKvHbrEuiBPs6bYKPfFXCSBFiGhs'
    }),
    RouterModule.forChild([kontaktRoute])
  ],
  declarations: [KontaktComponent]
})
export class KontaktModule {}
