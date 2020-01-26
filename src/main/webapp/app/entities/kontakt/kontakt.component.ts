import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  longitude = 7.809007;
  latitute = 51.678418;
  locationChosen = false;

  constructor() {}

  ngOnInit() {}

  onChoseLocation(event: any) {
    console.log(event);
    this.latitute = event.coords.lat;
    this.longitude = event.coords.lng;
    this.locationChosen = true;
  }
}
