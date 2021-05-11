import { IProposta } from '@/shared/model/proposta.model';

export interface ISeguradora {
  id?: number;
  nomeSeguradora?: string;
  propostas?: IProposta[] | null;
}

export class Seguradora implements ISeguradora {
  constructor(public id?: number, public nomeSeguradora?: string, public propostas?: IProposta[] | null) {}
}
