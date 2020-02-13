import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-kalkulator',
  templateUrl: './kalkulator.component.html'
})
export class KalkulatorComponent implements OnInit {
  onlyNumberRegexp = '^-?[0-9]\\d*(\\.\\d{1,2})?$';
  obliczone = false;
  wysokosc: number;
  szerokosc: number;
  dlugosc: number;
  temperaturaZewnatrz: number;
  temperaturaWewnatrz: number;
  izolacja: number;
  wynik: number;

  // Tworzenie formularza dla kalkulatora ze zmiennymi i walidacją
  kalkulatorForm = new FormGroup({
    wysokosc: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)]),
    szerokosc: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)]),
    dlugosc: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)]),
    temperaturaZewnatrz: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)]),
    temperaturaWewnatrz: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)]),
    izolacja: new FormControl('', [Validators.required, Validators.pattern(this.onlyNumberRegexp)])
  });

  constructor() {}

  ngOnInit() {}

  get f() {
    return this.kalkulatorForm.controls;
  }

  onSubmit() {
    this.obliczone = true;

    this.wysokosc = this.kalkulatorForm.get('wysokosc').value;
    this.szerokosc = this.kalkulatorForm.get('szerokosc').value;
    this.dlugosc = this.kalkulatorForm.get('dlugosc').value;
    this.temperaturaZewnatrz = this.kalkulatorForm.get('temperaturaZewnatrz').value;
    this.temperaturaWewnatrz = this.kalkulatorForm.get('temperaturaWewnatrz').value;
    this.izolacja = this.kalkulatorForm.get('izolacja').value;

    // wynik = objętość [wysokosc * szerokosc * dlugosc] * izolacja * (temperaturaWewnatrz - temperaturaZewnatrz)
    this.wynik = this.wysokosc * this.szerokosc * this.dlugosc * this.izolacja * (this.temperaturaWewnatrz - this.temperaturaZewnatrz);
  }

  resetForm() {
    this.kalkulatorForm.reset({
      wysokosc: '',
      szerokosc: '',
      dlugosc: '',
      temperaturaZewnatrz: '',
      temperaturaWewnatrz: '',
      izolacja: ''
    });

    this.obliczone = false;
  }
}
