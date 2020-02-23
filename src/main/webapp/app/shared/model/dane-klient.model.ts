import { IUser } from 'app/core/user/user.model';

export interface IDaneKlient {
  id?: number;
  imie?: string;
  nazwisko?: string;
  numerTelefonu?: number;
  email?: string;
  firma?: string;
  ulica?: string;
  miejscowosc?: string;
  kodPocztowy?: string;
  kraj?: string;
  nip?: number;
  user?: IUser;
}

export class DaneKlient implements IDaneKlient {
  constructor(
    public id?: number,
    public imie?: string,
    public nazwisko?: string,
    public numerTelefonu?: number,
    public email?: string,
    public firma?: string,
    public ulica?: string,
    public miejscowosc?: string,
    public kodPocztowy?: string,
    public kraj?: string,
    public nip?: number,
    public user?: IUser
  ) {}
}
