import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MagazynGrzewczySharedModule } from 'app/shared/shared.module';
import { MagazynGrzewczyCoreModule } from 'app/core/core.module';
import { MagazynGrzewczyAppRoutingModule } from './app-routing.module';
import { MagazynGrzewczyHomeModule } from './home/home.module';
import { MagazynGrzewczyEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MagazynGrzewczySharedModule,
    MagazynGrzewczyCoreModule,
    MagazynGrzewczyHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MagazynGrzewczyEntityModule,
    MagazynGrzewczyAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class MagazynGrzewczyAppModule {}
