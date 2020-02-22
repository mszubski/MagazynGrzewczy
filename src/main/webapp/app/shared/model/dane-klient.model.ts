export interface IDaneKlient {
  id?: number;
  imie?: string;
  nazwisko?: string;
  numerTelefonu?: number;
  email?: string;
  firma?: string;
  nip?: number;
  ulica?: string;
  miejscowosc?: string;
  kodPocztowy?: string;
  kraj?: string;
}

export class DaneKlient implements IDaneKlient {
  constructor(
    public id?: number,
    public imie?: string,
    public nazwisko?: string,
    public numerTelefonu?: number,
    public email?: string,
    public firma?: string,
    public nip?: number,
    public ulica?: string,
    public miejscowosc?: string,
    public kodPocztowy?: string,
    public kraj?: string
  ) {}
}
