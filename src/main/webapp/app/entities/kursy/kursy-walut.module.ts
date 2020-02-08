import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';

import { KursyWalutComponent } from './kursy-walut.component';

import { KursyWalutRoute } from './kursy-walut.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild([KursyWalutRoute])],
  declarations: [KursyWalutComponent]
})
export class KursyWalutModule {}
