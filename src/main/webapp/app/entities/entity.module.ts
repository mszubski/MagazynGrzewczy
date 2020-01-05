import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produkt',
        loadChildren: () => import('./produkt/produkt.module').then(m => m.MagazynGrzewczyProduktModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MagazynGrzewczyEntityModule {}
