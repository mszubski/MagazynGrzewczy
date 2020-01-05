import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';

import { JhiDocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [MagazynGrzewczySharedModule, RouterModule.forChild([docsRoute])],
  declarations: [JhiDocsComponent]
})
export class DocsModule {}
