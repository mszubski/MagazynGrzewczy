import { StatusProdukt } from 'app/shared/model/enumerations/status-produkt.model';
import { ProduktKategoriaEnum } from 'app/shared/model/enumerations/produkt-kategoria-enum.model';

export interface IProdukt {
  id?: number;
  nazwa?: string;
  cena?: number;
  opis?: string;
  status?: StatusProdukt;
  zdjecie?: string;
  stan?: number;
  kategoria?: ProduktKategoriaEnum;
}

export class Produkt implements IProdukt {
  constructor(
    public id?: number,
    public nazwa?: string,
    public cena?: number,
    public opis?: string,
    public status?: StatusProdukt,
    public zdjecie?: string,
    public stan?: number,
    public kategoria?: ProduktKategoriaEnum
  ) {}
}
