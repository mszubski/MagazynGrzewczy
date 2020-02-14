import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { StatusZamowienieEnum } from 'app/shared/model/enumerations/status-zamowienie-enum.model';

export interface IZamowienie {
  id?: number;
  cena?: number;
  status?: StatusZamowienieEnum;
  dataUtworzenia?: Moment;
  user?: IUser;
}

export class Zamowienie implements IZamowienie {
  constructor(
    public id?: number,
    public cena?: number,
    public status?: StatusZamowienieEnum,
    public dataUtworzenia?: Moment,
    public user?: IUser
  ) {}
}
