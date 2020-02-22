export interface IKursyWalutSymbole {
  success?: string;
  symbols?: any;
}

export class KursyWalutSymbole implements IKursyWalutSymbole {
  constructor(public success?: string, public symbols?: any) {}
}
