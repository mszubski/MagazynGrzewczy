import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';
import { DaneKlientComponent } from './dane-klient.component';
import { DaneKlientDetailComponent } from './dane-klient-detail.component';
import { DaneKlientUpdateComponent } from './dane-klient-update.component';
import { DaneKlientDeleteDialogComponent } from './dane-klient-delete-dialog.component';
import { daneKlientRoute } from './dane-klient.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild(daneKlientRoute)],
  declarations: [DaneKlientComponent, DaneKlientDetailComponent, DaneKlientUpdateComponent, DaneKlientDeleteDialogComponent],
  entryComponents: [DaneKlientDeleteDialogComponent]
})
export class MagazynGrzewczyDaneKlientModule {}
