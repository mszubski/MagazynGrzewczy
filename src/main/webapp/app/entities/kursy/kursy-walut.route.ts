import { Route } from '@angular/router';

import { KursyWalutComponent } from './kursy-walut.component';

export const KursyWalutRoute: Route = {
  path: '',
  component: KursyWalutComponent,
  data: {
    pageTitle: 'Kursy Walut'
  }
};
