import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produkt',
        loadChildren: () => import('./produkt/produkt.module').then(m => m.MagazynGrzewczyProduktModule)
      },
      {
        path: 'zamowienie-wpis',
        loadChildren: () => import('./zamowienie-wpis/zamowienie-wpis.module').then(m => m.MagazynGrzewczyZamowienieWpisModule)
      },
      {
        path: 'kontakt',
        loadChildren: () => import('./kontakt/kontakt.module').then(m => m.KontaktModule)
      },
      {
        path: 'kursy-walut',
        loadChildren: () => import('./kursy/kursy-walut.module').then(m => m.KursyWalutModule)
      },
      {
        path: 'kalkulator',
        loadChildren: () => import('./kalkulator/kalkulator.module').then(m => m.KalkulatorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MagazynGrzewczyEntityModule {}
