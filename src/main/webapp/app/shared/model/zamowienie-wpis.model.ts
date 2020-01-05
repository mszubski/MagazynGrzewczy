import { IUser } from 'app/core/user/user.model';
import { IProdukt } from 'app/shared/model/produkt.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';
import { StatusZamowieniaEnum } from 'app/shared/model/enumerations/status-zamowienia-enum.model';

export interface IZamowienieWpis {
  id?: number;
  ilosc?: number;
  cena?: number;
  status?: StatusEnum;
  statusZamowienia?: StatusZamowieniaEnum;
  user?: IUser;
  produkt?: IProdukt;
}

export class ZamowienieWpis implements IZamowienieWpis {
  constructor(
    public id?: number,
    public ilosc?: number,
    public cena?: number,
    public status?: StatusEnum,
    public statusZamowienia?: StatusZamowieniaEnum,
    public user?: IUser,
    public produkt?: IProdukt
  ) {}
}
