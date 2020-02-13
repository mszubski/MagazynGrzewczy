import { Route } from '@angular/router';

import { KalkulatorComponent } from './kalkulator.component';

export const kalkulatorRoute: Route = {
  path: '',
  component: KalkulatorComponent,
  data: {
    pageTitle: 'Kalkulator zapotrzebowania budynku na moc cieplną'
  }
};
