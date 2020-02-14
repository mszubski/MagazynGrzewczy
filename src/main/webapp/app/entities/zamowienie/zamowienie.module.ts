import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';
import { ZamowienieComponent } from './zamowienie.component';
import { ZamowienieDetailComponent } from './zamowienie-detail.component';
import { ZamowienieUpdateComponent } from './zamowienie-update.component';
import { ZamowienieDeleteDialogComponent } from './zamowienie-delete-dialog.component';
import { zamowienieRoute } from './zamowienie.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild(zamowienieRoute)],
  declarations: [ZamowienieComponent, ZamowienieDetailComponent, ZamowienieUpdateComponent, ZamowienieDeleteDialogComponent],
  entryComponents: [ZamowienieDeleteDialogComponent]
})
export class MagazynGrzewczyZamowienieModule {}
