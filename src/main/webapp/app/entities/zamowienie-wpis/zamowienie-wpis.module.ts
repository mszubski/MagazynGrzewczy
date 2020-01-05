import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';
import { ZamowienieWpisComponent } from './zamowienie-wpis.component';
import { ZamowienieWpisDetailComponent } from './zamowienie-wpis-detail.component';
import { ZamowienieWpisUpdateComponent } from './zamowienie-wpis-update.component';
import { ZamowienieWpisDeleteDialogComponent } from './zamowienie-wpis-delete-dialog.component';
import { zamowienieWpisRoute } from './zamowienie-wpis.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild(zamowienieWpisRoute)],
  declarations: [
    ZamowienieWpisComponent,
    ZamowienieWpisDetailComponent,
    ZamowienieWpisUpdateComponent,
    ZamowienieWpisDeleteDialogComponent
  ],
  entryComponents: [ZamowienieWpisDeleteDialogComponent]
})
export class MagazynGrzewczyZamowienieWpisModule {}
