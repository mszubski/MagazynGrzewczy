import { Component, OnInit } from '@angular/core';
import { MouseEvent } from '@agm/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { JhiAlertService } from 'ng-jhipster';
import { KontaktService } from 'app/entities/kontakt/kontakt.service';

@Component({
  selector: 'jhi-kontakt',
  templateUrl: './kontakt.component.html'
})
export class KontaktComponent implements OnInit {
  lat: number = 52.3060318;
  lng: number = 20.9881805;
  zoom: number = 10;
  label?: string;
  isNavbarCollapsed: boolean;
  dane: string;
  temat: string;
  telefon: string;
  email: string;
  poleTekstowe: string;
  wyslane = false;

  // Tworzenie formularza ze zmiennymi
  form = new FormGroup({
    dane: new FormControl('', [Validators.required]),
    temat: new FormControl('', [Validators.required]),
    telefon: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    poleTekstowe: new FormControl('')
  });

  constructor(protected kontaktService: KontaktService) {
    this.kontaktService = kontaktService;
  }

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

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.wyslane = true;

    this.dane = this.form.get('dane').value;
    this.temat = this.form.get('temat').value;
    this.email = this.form.get('email').value;
    this.telefon = this.form.get('telefon').value;
    this.poleTekstowe = this.form.get('poleTekstowe').value;

    this.sendMail();

    this.resetForm();
  }

  resetForm() {
    this.form.reset({ dane: '', temat: '', email: '', poleTekstowe: '' });
  }

  sendMail() {
    const formData = new FormData();

    formData.append('dane', this.dane);
    formData.append('temat', this.temat);
    formData.append('email', this.email);
    formData.append('telefon', this.telefon);
    formData.append('poleTekstowe', this.poleTekstowe);

    this.kontaktService.sendKontaktMail(formData);
  }
}

// just an interface for type safety.
interface marker {
  lat: number;
  lng: number;
  label?: string;
  draggable: boolean;
}
