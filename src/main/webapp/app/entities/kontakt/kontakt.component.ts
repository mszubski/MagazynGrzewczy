import { Component, OnInit } from '@angular/core';
import { MouseEvent } from '@agm/core';

@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  lat: number = 52.3060318;
  lng: number = 20.9881805;
  zoom: number = 10;
  label?: string;

  constructor() {}

  ngOnInit() {}

  clickedMarker(label: string, index: number) {
    console.log(`Kliknąłeś punkt na mapie: ${label || index}`);
  }

  markerDragEnd(m: marker, $event: MouseEvent) {
    console.log('dragEnd', m, $event);
  }

  mapClicked($event: MouseEvent) {
    this.markers.push({
      lat: $event.coords.lat,
      lng: $event.coords.lng,
      draggable: false
    });
  }

  markers: marker[] = [
    {
      lat: 52.3060318,
      lng: 20.9881805,
      label: 'MG',
      draggable: false
    }
  ];
}

// just an interface for type safety.
interface marker {
  lat: number;
  lng: number;
  label?: string;
  draggable: boolean;
}
