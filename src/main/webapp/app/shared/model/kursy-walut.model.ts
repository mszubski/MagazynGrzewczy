export interface IKursyWalut {
  success?: string;
  timestamp?: string;
  base?: string;
  date?: any;
  rates?: any;
}

export class KursyWalut implements IKursyWalut {
  constructor(public success?: string, public timestamp?: string, public base?: string, public date?: any, public rates?: any) {}
}
