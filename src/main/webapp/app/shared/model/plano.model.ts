import { IProposta } from '@/shared/model/proposta.model';

export interface IPlano {
  id?: number;
  nomePlano?: string;
  propostas?: IProposta[] | null;
}

export class Plano implements IPlano {
  constructor(public id?: number, public nomePlano?: string, public propostas?: IProposta[] | null) {}
}
