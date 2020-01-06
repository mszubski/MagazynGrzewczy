import { Route } from '@angular/router';

import { KontaktComponent } from './kontakt.component';

export const kontaktRoute: Route = {
  path: '',
  component: KontaktComponent,
  data: {
    pageTitle: 'Kontakt'
  }
};
